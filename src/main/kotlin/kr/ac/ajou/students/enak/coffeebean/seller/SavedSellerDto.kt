package kr.ac.ajou.students.enak.coffeebean.seller

import kr.ac.ajou.students.enak.coffeebean.auth.AuthService

data class SavedSellerDto(
    val loginId: String,
    val businessName: String,
    val token: AuthService.TokenEntry,
) {
    constructor(entity: SellerEntity, token: AuthService.TokenEntry) : this(
        loginId = entity.loginId,
        businessName = entity.companyName,
        token = token,
    )
}