package kr.ac.ajou.students.enak.coffeebean.seller

import io.swagger.annotations.ApiModelProperty

data class SellerDto(
    @ApiModelProperty(
        notes = "생산자 등록 ID",
        example = "1",
        required = true,
    )
    val id: Long,

    @ApiModelProperty(
        notes = "상호명",
        example = "스타벅스커피코리아",
        required = true,
    )
    val companyName: String,

    @ApiModelProperty(
        notes = "사업자 등록 번호",
        example = "314-15-14380",
        required = true,
    )
    val companyRegistrationNumber: Long,

    @ApiModelProperty(
        notes = "사업장 주소지",
        example = "경기도 수원시 영통주 월드컵로208번길 37",
        required = true,
    )
    val businessAddress: String,
) {
}