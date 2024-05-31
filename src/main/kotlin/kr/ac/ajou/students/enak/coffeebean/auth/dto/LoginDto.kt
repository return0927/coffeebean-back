package kr.ac.ajou.students.enak.coffeebean.auth.dto

import io.swagger.annotations.ApiModelProperty

data class LoginDto(
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
) {
}