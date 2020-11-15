package com.krasnov.monitoring.repository.device

import com.krasnov.monitoring.model.device.DeviceType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeviceTypeRepository : JpaRepository<DeviceType, Int>