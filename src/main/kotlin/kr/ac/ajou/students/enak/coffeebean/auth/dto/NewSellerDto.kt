package kr.ac.ajou.students.enak.coffeebean.auth.dto

import io.swagger.annotations.ApiModelProperty

data class NewSellerDto(
    @ApiModelProperty(
        value = "로그인 ID",
        example = "bc1916",
        required = true,
    )
    val loginId: String,

    @ApiModelProperty(
        value = "로그인 PW",
        example = "1q2w3e4r",
        required = true,
    )
    val password: String,

    @ApiModelProperty(
        value = "상호명",
        example = "스타벅스커피코리아",
        required = true,
    )
    val companyName: String,

    @ApiModelProperty(
        value = "사업자 등록번호",
        example = "31412123456",
        required = true,
    )
    val companyRegistrationNumber: Long,

    @ApiModelProperty(
        value = "사업장 소재지",
        example = "경기도 수원시 영통주 월드컵로206번길",
        required = true,
    )
    val businessAddress: String,
) {

}