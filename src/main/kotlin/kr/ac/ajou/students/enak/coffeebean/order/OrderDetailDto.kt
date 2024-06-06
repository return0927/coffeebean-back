package kr.ac.ajou.students.enak.coffeebean.order

import java.util.*

data class OrderDetailDto(
    val orderId: Int,
    val name: String,
    val status: OrderStatus,
    val price: Long,
    val amount: Int,
    val recipient: String,
    val createAt: Date,
) {
}