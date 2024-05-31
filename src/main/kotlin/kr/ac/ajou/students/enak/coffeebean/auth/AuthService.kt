package kr.ac.ajou.students.enak.coffeebean.auth

import io.jsonwebtoken.Jwts
import kr.ac.ajou.students.enak.coffeebean.auth.dto.LoginDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    @Value("\${coffeebean.secretKey}")
    secretKey: String,

    @Value("\${coffeebean.valid-duration}")
    validDurationInSeconds: Long,
) {
    private val secretKey: String = Base64.getEncoder().encodeToString(secretKey.toByteArray())

    fun createToken(customer: LoginDto) {
        val claims = Jwts.claims().setSubject(customer.loginId)
    }
}
