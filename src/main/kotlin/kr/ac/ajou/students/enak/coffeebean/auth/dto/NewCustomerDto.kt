package kr.ac.ajou.students.enak.coffeebean.auth.dto

import io.swagger.annotations.ApiModelProperty

data class NewCustomerDto(
    @ApiModelProperty(
        notes = "로그인 ID",
        example = "bc1916",
        required = true,
    )
    val loginId: String,

    @ApiModelProperty(
        notes = "로그인 PW",
        example = "1q2w3e4r",
        required = true,
    )
    val password: String,

    @ApiModelProperty(
        notes = "이름",
        example = "은학",
        required = true,
    )
    val lastName: String,

    @ApiModelProperty(
        notes = "성",
        example = "이",
        required = true,
    )
    val firstName: String,

    @ApiModelProperty(
        notes = "성별",
        example = "0",
        required = true,
    )
    val gender: Int,

    @ApiModelProperty(
        notes = "생년월일",
        example = "2001-01-01",
        required = true,
    )
    val birthDate: String,

    @ApiModelProperty(
        notes = "주소",
        example = "경기도 수원시 영통구 월드컵로 206",
        required = true,
    )
    val address: String,

    @ApiModelProperty(
        notes = "전화번호",
        example = "010-1234-12341",
        required = true,
    )
    val phone: String,
) {
}