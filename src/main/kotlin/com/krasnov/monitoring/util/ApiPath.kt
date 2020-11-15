package com.krasnov.monitoring.util

class ApiPath {
    companion object {
        private const val API = "/api"
        private const val V1 = "/v1"

        const val API_V1 = API + V1

        const val ID = "/{id}"

        const val DEVICE = "/device"
    }
}