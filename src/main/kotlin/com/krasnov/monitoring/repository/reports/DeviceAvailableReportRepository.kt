package com.krasnov.monitoring.repository.reports

import com.krasnov.monitoring.model.reports.DeviceAvailableReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeviceAvailableReportRepository : JpaRepository<DeviceAvailableReport, Int>