package com.krasnov.monitoring.model.device

import javax.persistence.*

@Entity
@Table(name = "device_brands")
class DeviceBrand(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        val name: String,

        val site: String,

        val icon: String
)