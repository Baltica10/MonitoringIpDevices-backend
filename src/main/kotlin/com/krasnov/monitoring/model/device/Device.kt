package com.krasnov.monitoring.model.device

import javax.persistence.*

@Entity
@Table(name = "devices")
class Device(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        @ManyToOne
        @JoinColumn(name = "type_id")
        val type: DeviceType,

        @ManyToOne
        @JoinColumn(name = "brand_id")
        val brand: DeviceBrand,

        val model: String,

        @Column(name = "serial_number")
        val serialNumber: String,

        val description: String?,

        val image: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "settings_id")
        val settings: DeviceSettings?,

        @Column(name = "check_available")
        val checkAvailable: Boolean,

        @Column(name = "check_page_count")
        val checkPageCount: Boolean,

        @Column(name = "notify_tlg")
        val notifyTlg: Boolean,

        @Column(name = "notify_email")
        val notifyEmail: Boolean

)