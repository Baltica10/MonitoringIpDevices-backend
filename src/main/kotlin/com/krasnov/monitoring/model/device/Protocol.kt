package com.krasnov.monitoring.model.device

import javax.persistence.*

@Entity
@Table(name = "protocols")
class Protocol(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        val value: String
)