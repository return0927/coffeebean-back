package kr.ac.ajou.students.enak.coffeebean.auth.dto

import kr.ac.ajou.students.enak.coffeebean.AccountType

data class ScopedLoginDto(
    val originalDto: LoginDto,
    val scope: AccountType,
) {
    val loginId: String
        get() = originalDto.loginId

    val password: String
        get() = originalDto.password
}