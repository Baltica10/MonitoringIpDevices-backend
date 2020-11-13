package com.krasnov.monitoring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MonitoringBackendApplication

fun main(args: Array<String>) {
    runApplication<MonitoringBackendApplication>(*args)
}
