package kr.ac.ajou.students.enak.coffeebean.order

data class UpdateOrderDto(
    val status: OrderStatus? = null,
    val deliverAddress: String? = null,
    val recipient: String? = null,
)
