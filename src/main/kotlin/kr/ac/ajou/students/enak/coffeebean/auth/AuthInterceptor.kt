package kr.ac.ajou.students.enak.coffeebean.auth

import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthInterceptor(
    private val authService: AuthService,
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

        return super.preHandle(request, response, handler)
    }
}