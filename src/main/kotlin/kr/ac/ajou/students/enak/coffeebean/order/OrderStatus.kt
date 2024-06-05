package kr.ac.ajou.students.enak.coffeebean.order

enum class OrderStatus {
    PENDING,
    PREPARING,
    DELIVERING,
    DELIVERED,
    CANCELED,
    ;

    companion object {
        operator fun get(code: String): OrderStatus = entries.first { it.name == code }
    }
}