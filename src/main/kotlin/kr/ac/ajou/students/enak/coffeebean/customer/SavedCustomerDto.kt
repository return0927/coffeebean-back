package kr.ac.ajou.students.enak.coffeebean.customer

import kr.ac.ajou.students.enak.coffeebean.auth.AuthService

data class SavedCustomerDto(
    val loginId: String,
    val firstName: String,
    val lastName: String,
    val token: AuthService.TokenEntry,
) {
    constructor(entity: CustomerEntity, token: AuthService.TokenEntry) : this(
        loginId = entity.loginId,
        firstName = entity.firstName,
        lastName = entity.lastName,
        token = token,
    )
}