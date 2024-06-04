package kr.ac.ajou.students.enak.coffeebean.auth

import kr.ac.ajou.students.enak.coffeebean.AccountType
import kr.ac.ajou.students.enak.coffeebean.customer.CustomerService
import kr.ac.ajou.students.enak.coffeebean.errors.ReportingError
import kr.ac.ajou.students.enak.coffeebean.seller.SellerService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthInterceptor(
    private val authService: AuthService,
    private val sellerService: SellerService,
    private val customerService: CustomerService,
) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        request.setAttribute("user", null)

        val header = request.headerNames.toList().firstOrNull {
            it.lowercase() == "authorization"
        } ?: return true
        val value = request.getHeader(header)
        val (_, token) = value.split(" ", limit = 2)


        val subject = authService.validateToken(token)
        val entity = when (subject.type) {
            AccountType.SELLER -> sellerService.getViaLogin(subject.id)
            AccountType.CUSTOMER -> customerService.getViaLogin(subject.id)
        } ?: throw ReportingError("유효하지 않은 토큰입니다.", HttpStatus.NOT_FOUND)

        request.setAttribute("user", entity)
        request.setAttribute("userType", subject.type)
        return super.preHandle(request, response, handler)
    }
}