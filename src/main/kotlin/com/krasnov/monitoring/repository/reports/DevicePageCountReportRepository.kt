package com.krasnov.monitoring.repository.reports

import com.krasnov.monitoring.model.reports.DevicePageCountReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DevicePageCountReportRepository : JpaRepository<DevicePageCountReport, Int>