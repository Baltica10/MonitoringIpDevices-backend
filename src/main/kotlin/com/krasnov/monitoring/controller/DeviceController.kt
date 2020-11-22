package com.krasnov.monitoring.controller

import com.krasnov.monitoring.service.DeviceService
import com.krasnov.monitoring.util.ApiPath.Companion.API_V1
import com.krasnov.monitoring.util.ApiPath.Companion.DEVICE
import com.krasnov.monitoring.util.ApiPath.Companion.ID
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(API_V1 + DEVICE)
class DeviceController(
        private val service: DeviceService
) {

    @GetMapping(ID)
    fun getDeviceAvailableStatus(@PathVariable id: Int): ResponseEntity<Boolean> {
        return ResponseEntity.ok(service.checkDeviceStatus(id))
    }
}