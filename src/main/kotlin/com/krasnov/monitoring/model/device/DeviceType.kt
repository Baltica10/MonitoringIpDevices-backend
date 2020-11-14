package com.krasnov.monitoring.model.device

import javax.persistence.*

@Entity
@Table(name = "device_types")
class DeviceType(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        val name: String,

        val description: String,

        val icon: String
)