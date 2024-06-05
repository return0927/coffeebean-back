package kr.ac.ajou.students.enak.coffeebean.order

data class OrderDto(
    val orderId: Int,
    val status: OrderStatus,
    val price: Long,
    val amount: Int,
    val recipient: String,
) {
    constructor(entity: OrderEntity) : this(
        orderId = entity.id!!,
        status = entity.status,
        price = entity.price,
        amount = entity.amount,
        recipient = entity.recipient,
    )
}
