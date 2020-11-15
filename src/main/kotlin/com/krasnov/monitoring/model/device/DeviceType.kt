package com.krasnov.monitoring.model.device

import javax.persistence.*

@Entity
@Table(name = "device_types")
data class DeviceType(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        val name: String,

        val description: String,

        val icon: String
)