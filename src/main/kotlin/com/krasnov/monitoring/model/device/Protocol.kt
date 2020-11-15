package com.krasnov.monitoring.model.device

import javax.persistence.*

@Entity
@Table(name = "protocols")
data class Protocol(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        val value: String
)