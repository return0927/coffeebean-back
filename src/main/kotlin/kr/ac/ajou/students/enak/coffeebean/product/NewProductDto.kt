package kr.ac.ajou.students.enak.coffeebean.product

data class NewProductDto(
    val name: String,
    val origins: String,
    val quantity: Long,
    val processing: String,
    val grinding: String,
    val price: Long,
    val discounts: Long,
    val imageUrl: String,
)
