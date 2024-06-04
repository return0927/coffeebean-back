package kr.ac.ajou.students.enak.coffeebean.errors

import org.springframework.http.HttpStatus

open class ReportingError(
    message: String,
    cause: Throwable? = null,
    val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
) : BackendError(message, cause) {
    constructor(message: String, httpStatus: HttpStatus) : this(message, null, httpStatus)
}
