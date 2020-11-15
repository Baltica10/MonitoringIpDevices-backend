package com.krasnov.monitoring.exception

abstract class CommonException(message: String, cause: Throwable?) : RuntimeException(message, cause)