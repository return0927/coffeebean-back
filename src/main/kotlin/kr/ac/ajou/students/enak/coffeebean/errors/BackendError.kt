package kr.ac.ajou.students.enak.coffeebean.errors

open class BackendError(
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause)
