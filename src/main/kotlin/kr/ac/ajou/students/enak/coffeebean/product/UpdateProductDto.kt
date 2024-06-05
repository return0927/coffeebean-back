package kr.ac.ajou.students.enak.coffeebean.product

data class UpdateProductDto(
    val name: String? = null,
    val origins: String? = null,
    val quantity: Long? = null,
    val processing: String? = null,
    val grinding: String? = null,
    val price: Long? = null,
    val discounts: Long? = null,
    val imageUrl: String? = null,
)
