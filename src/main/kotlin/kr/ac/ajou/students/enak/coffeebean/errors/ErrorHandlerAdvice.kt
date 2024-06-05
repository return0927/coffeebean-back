package kr.ac.ajou.students.enak.coffeebean.errors

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorHandlerAdvice {
    private val logger = LoggerFactory.getLogger(ErrorHandlerAdvice::class.java)

    data class ErrorDto(
        val message: String,
        val error: Boolean = true,
    ) {
        constructor(message: String? = null) : this(message ?: "처리하는 도중 오류가 발생했습니다.")
    }

    @ExceptionHandler(Throwable::class)
    fun handleErrors(e: Throwable): ResponseEntity<*> {
        logger.warn("처리 도중 오류가 발생했습니다.", e)
        if (e is ReportingError) return ResponseEntity(ErrorDto(e.message), e.httpStatus)
        return ResponseEntity(ErrorDto(), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}