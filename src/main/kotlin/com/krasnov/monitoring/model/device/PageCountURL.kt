package com.krasnov.monitoring.model.device

import javax.persistence.*

@Entity
@Table(name = "page_count_url")
class PageCountURL(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        val value: String,

        val pattern: String
)