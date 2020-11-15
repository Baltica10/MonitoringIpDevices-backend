package com.krasnov.monitoring.repository.device

import com.krasnov.monitoring.model.device.Device
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeviceRepository : JpaRepository<Device, Int>