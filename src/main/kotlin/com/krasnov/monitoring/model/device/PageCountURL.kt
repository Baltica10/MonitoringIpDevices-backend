package com.krasnov.monitoring.model.device

import javax.persistence.*

@Entity
@Table(name = "page_count_url")
data class PageCountURL(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        val value: String,

        val pattern: String
)