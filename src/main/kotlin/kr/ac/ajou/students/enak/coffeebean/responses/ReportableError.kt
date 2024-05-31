package kr.ac.ajou.students.enak.coffeebean.responses

import org.springframework.http.HttpStatus

class ReportableError(
    message: String,
    val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    cause: Throwable? = null,
) : RuntimeException(message, cause)
