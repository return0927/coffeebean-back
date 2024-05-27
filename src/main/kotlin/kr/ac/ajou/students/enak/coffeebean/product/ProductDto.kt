package kr.ac.ajou.students.enak.coffeebean.product

import io.swagger.annotations.ApiModelProperty

data class ProductDto(
    @ApiModelProperty(
        notes = "상품 ID",
        example = "1",
        required = true,
    )
    val productId: Int,

    @ApiModelProperty(
        notes = "브랜드(회사) 이름",
        example = "A Company Name",
        required = true,
    )
    val brandName: String,

    @ApiModelProperty(
        notes = "원산지",
        example = "Africa",
        required = true,
    )
    val origins: String,

    @ApiModelProperty(
        notes = "재고",
        example = "1000",
        required = true,
    )
    val quantity: Int,

    @ApiModelProperty(
        notes = "가공법",
        required = true,
    )
    val processing: String,

    @ApiModelProperty(
        notes = "원두 분쇄",
        required = true,
    )
    val grinding: String,

    @ApiModelProperty(
        notes = "가격",
        example = "10000",
        required = true,
    )
    val price: Int,

    @ApiModelProperty(
        notes = "할인액",
        example = "0",
        required = true,
    )
    val discounts: Int,
) {
}
