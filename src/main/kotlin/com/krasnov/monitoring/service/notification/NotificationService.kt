package com.krasnov.monitoring.service.notification

import com.krasnov.monitoring.model.device.Device

interface NotificationService {

    fun sendDeviceAlert(device: Device)
}