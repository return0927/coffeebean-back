package kr.ac.ajou.students.enak.coffeebean.auth.dto

import kr.ac.ajou.students.enak.coffeebean.auth.AuthService
import java.util.*

data class TokenDto(
    val token: String,
    val expiresAt: Date,
) {
    constructor(entry: AuthService.TokenEntry) : this(
        token = entry.token,
        expiresAt = entry.expiresAt,
    )
}