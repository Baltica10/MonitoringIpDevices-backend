package com.krasnov.monitoring.exception

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomExceptionHandler() : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [(ValidationException::class)])
    fun handleValidationException(ex: ValidationException): ResponseEntity<CustomException> {
        logException(ex)
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CustomException(ex.message, HttpStatus.FORBIDDEN.value()))
    }

    @ExceptionHandler(value = [(EntityNotFoundException::class)])
    fun handleNotFoundException(ex: EntityNotFoundException): ResponseEntity<CustomException> {
        logException(ex)
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CustomException(ex.message, HttpStatus.NOT_FOUND.value()))
    }

    private fun logException(ex: Exception) {
        logger.warn("Exception message: ${ex.message}")
        logger.debug("Trace: $ex")
    }

    class CustomException(val message: String?, val status: Int)
}