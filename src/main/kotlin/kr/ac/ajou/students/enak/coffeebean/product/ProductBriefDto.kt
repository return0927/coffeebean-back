package kr.ac.ajou.students.enak.coffeebean.product

data class ProductBriefDto(
    val productId: Long,
    val name: String,
    val brandName: String,
    val price: Long,
    val discounts: Long,
    val quantity: Long,
    val imageUrl: String,
) {
    constructor(dto: ProductDto) : this(
        dto.productId,
        dto.name,
        dto.brandName,
        dto.price,
        dto.discounts,
        dto.quantity,
        dto.imageUrl,
    )
}
