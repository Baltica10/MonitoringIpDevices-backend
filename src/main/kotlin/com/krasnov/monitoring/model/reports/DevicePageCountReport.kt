package com.krasnov.monitoring.model.reports

import com.krasnov.monitoring.model.device.Device
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "device_page_count_reports")
data class DevicePageCountReport(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @ManyToOne
        @JoinColumn(name = "device_id")
        val device: Device,

        @Column(name = "used_url")
        val usedUrl: String = device.getPageCountURL(),

        @Column(name = "created_at")
        val createdAt: LocalDateTime = LocalDateTime.now(),

        @Column(name = "page_count")
        val pageCount: Int
)