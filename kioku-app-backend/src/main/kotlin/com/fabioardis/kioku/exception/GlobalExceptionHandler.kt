package com.fabioardis.kioku.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrors(ex: MethodArgumentNotValidException): ResponseEntity<ApiErrorResponse> {
        val errors = ex.bindingResult.fieldErrors.map {
            "${it.field}: ${it.defaultMessage}"
        }

        return ResponseEntity.badRequest().body(
            ApiErrorResponse(
                message = "Validation failed",
                errors = errors,
                timestamp = LocalDateTime.now()
            )
        )
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(ex: ResourceNotFoundException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiErrorResponse(
                message = ex.message ?: "Resource not found",
                timestamp = LocalDateTime.now()
            )
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ApiErrorResponse(
                message = "Internal server error",
                timestamp = LocalDateTime.now()
            )
        )
    }
}

data class ApiErrorResponse(
    val message: String,
    val errors: List<String> = emptyList(),
    val timestamp: LocalDateTime
)

class ResourceNotFoundException(message: String) : RuntimeException(message)