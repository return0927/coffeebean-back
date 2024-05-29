package kr.ac.ajou.students.enak.coffeebean.product

data class ProductBriefDto(
    val productId: Long,
    val brandName: String,
    val price: Long,
    val discounts: Long,
) {
    constructor(dto: ProductDto) : this(dto.productId, dto.brandName, dto.price, dto.discounts)
}
