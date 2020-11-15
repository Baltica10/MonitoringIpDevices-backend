package com.krasnov.monitoring.model.device

import javax.persistence.*

@Entity
@Table(name = "device_brands")
data class DeviceBrand(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        val name: String,

        val site: String,

        val icon: String
)