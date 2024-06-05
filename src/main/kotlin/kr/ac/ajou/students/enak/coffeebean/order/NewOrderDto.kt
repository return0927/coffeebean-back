package kr.ac.ajou.students.enak.coffeebean.order

data class NewOrderDto(
    val itemId: Int,
    val amount: Int,
    val deliverAddress: String,
    val recipient: String,
)
