package com.krasnov.monitoring.job

import com.krasnov.monitoring.repository.device.DeviceRepository
import com.krasnov.monitoring.repository.reports.*
import com.krasnov.monitoring.service.DeviceService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class DeviceReportJob(
        private val deviceRepository: DeviceRepository,
        private val availableReportRepository: DeviceAvailableReportRepository,
        private val pageCountReportRepository: DevicePageCountReportRepository,
        private val deviceService: DeviceService
) {

    @Scheduled(cron = "\${cron.check-devices}")
    fun checkAvailableDevices() {
        val reports = deviceRepository.findAllByCheckAvailableTrue().stream()
                .map { deviceService.getDeviceAvailableReport(it) }
                .collect(Collectors.toList())

        availableReportRepository.saveAll(reports)
    }

    @Scheduled(cron = "\${cron.page-count}")
    fun createMfuPageCountReports() {
        val reports = deviceRepository.findAllByCheckPageCountTrue().stream()
                .map { deviceService.getDevicePageCountReport(it) }
                .collect(Collectors.toList())

        pageCountReportRepository.saveAll(reports)
    }
}