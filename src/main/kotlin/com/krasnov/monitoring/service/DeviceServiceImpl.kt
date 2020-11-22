package com.krasnov.monitoring.service

import com.krasnov.monitoring.exception.EntityNotFoundException
import com.krasnov.monitoring.model.device.Device
import com.krasnov.monitoring.model.reports.*
import com.krasnov.monitoring.repository.device.DeviceRepository
import org.jsoup.Jsoup
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.*
import java.time.Duration

@Service
class DeviceServiceImpl(
        private val deviceRepository: DeviceRepository,
) : DeviceService {

    override fun checkDeviceStatus(deviceId: Int): Boolean {

        val device = deviceRepository.findByIdOrNull(deviceId)
                ?: throw EntityNotFoundException("Device with ID:$deviceId not found")

        val report = getDeviceAvailableReport(device)

        return report.isAvailable
    }

    override fun getDeviceAvailableReport(device: Device): DeviceAvailableReport {
        try {
            val httpClient: HttpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(5))
                    .build()
            val requestHead = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(device.getCheckAvailableURL()))
                    .build()
            val httpResponse = httpClient.send(requestHead, HttpResponse.BodyHandlers.ofString())

            return DeviceAvailableReport(
                    device = device,
                    isAvailable = httpResponse.statusCode() == HttpStatus.OK.value())

        } catch (ex: Exception) {
            return DeviceAvailableReport(
                    device = device,
                    isAvailable = false
            )
        }
    }

    override fun getDevicePageCountReport(device: Device): DevicePageCountReport {
        try {
            val doc = Jsoup.connect(device.getPageCountURL()).get()
            val rows = doc.select("tr")

            rows.forEach {
                val tds = it.select("td")
                if (tds[0].text() == device.settings?.pageCountURL?.pattern) {
                    return DevicePageCountReport(
                            device = device,
                            pageCount = tds[1].text().toInt()
                    )
                }
            }
            return DevicePageCountReport(
                    device = device,
                    pageCount = 0
            )
        } catch (ex: Exception) {
            return DevicePageCountReport(
                    device = device,
                    pageCount = 0
            )
        }
    }
}