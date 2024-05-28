package kr.ac.ajou.students.enak.coffeebean.product

import kr.ac.ajou.students.enak.coffeebean.abc.Entity

data class ProductEntity(
    val id: Long,
    var brandName: String,
    var origins: String? = null,
    var quantity: Long,
    var processing: String? = null,
    var grinding: String? = null,
    var price: Long,
    var discounts: Long,
) : Entity
