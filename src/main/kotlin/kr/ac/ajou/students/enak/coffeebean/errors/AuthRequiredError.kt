package kr.ac.ajou.students.enak.coffeebean.errors

import kr.ac.ajou.students.enak.coffeebean.AccountType
import org.springframework.http.HttpStatus

class AuthRequiredError(
    val requiredAccountType: AccountType? = null,
    val nowAccountType: AccountType? = null,
) : ReportingError(
    if (nowAccountType == null) "로그인이 필요합니다." else when (requiredAccountType) {
        AccountType.SELLER -> "판매자로 로그인해야 합니다."
        AccountType.CUSTOMER -> "고객으로 로그인해야 합니다."
        else -> "로그인이 필요합니다."
    }, HttpStatus.UNAUTHORIZED
)
