package com.krasnov.monitoring.service.device

import com.krasnov.monitoring.model.device.Device
import com.krasnov.monitoring.model.reports.*

interface DeviceService {
    fun checkDeviceStatus(deviceId: Int): Boolean

    fun getDeviceAvailableReport(device: Device): DeviceAvailableReport

    fun getDevicePageCountReport(device: Device): DevicePageCountReport
}