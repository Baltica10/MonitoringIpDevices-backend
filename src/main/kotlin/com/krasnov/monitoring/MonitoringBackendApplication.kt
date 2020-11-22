package com.krasnov.monitoring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.*

@EnableAsync
@EnableScheduling
@SpringBootApplication
class MonitoringBackendApplication

fun main(args: Array<String>) {
    runApplication<MonitoringBackendApplication>(*args)
}
