package com.krasnov.monitoring.model.device

import javax.persistence.*

@Entity
@Table(name = "device_settings")
data class DeviceSettings(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @ManyToOne
        @JoinColumn(name = "protocol_id")
        val protocol: Protocol,

        val host: String,

        val port: Int?,

        @ManyToOne
        @JoinColumn(name = "page_count_url_id")
        val pageCountURL: PageCountURL?
)