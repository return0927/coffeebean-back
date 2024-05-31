package kr.ac.ajou.students.enak.coffeebean

import kotlinx.serialization.Serializable

@Serializable
enum class AccountType {
    PRODUCER,
    SELLER,
    ;

}