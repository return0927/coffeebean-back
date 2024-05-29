package kr.ac.ajou.students.enak.coffeebean.seller

data class SellerBriefDto(
    val id: Long,
    val companyName: String,
    val businessAddress: String,
) {
    constructor(dto: SellerDto) : this(dto.id, dto.companyName, dto.businessAddress)
}