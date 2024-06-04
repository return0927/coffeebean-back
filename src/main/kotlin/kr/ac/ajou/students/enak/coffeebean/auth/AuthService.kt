package kr.ac.ajou.students.enak.coffeebean.auth

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kr.ac.ajou.students.enak.coffeebean.AccountType
import kr.ac.ajou.students.enak.coffeebean.auth.dto.ScopedLoginDto
import kr.ac.ajou.students.enak.coffeebean.customer.CustomerEntity
import kr.ac.ajou.students.enak.coffeebean.responses.ReportableError
import kr.ac.ajou.students.enak.coffeebean.seller.SellerEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    @Value("\${coffeebean.secretKey}")
    secretKey: String,

    @Value("\${coffeebean.valid-duration}")
    private val validDurationInSeconds: Long,
) {
    private val json = Json {
        prettyPrint = false
    }

    private val secretKey: String = Base64.getEncoder().encodeToString(secretKey.toByteArray())

    private val tokens: MutableMap<String, TokenEntry> = hashMapOf()

    fun createToken(user: CustomerEntity) = createToken(AuthScope(AccountType.CUSTOMER, user.loginId))

    fun createToken(user: SellerEntity) = createToken(AuthScope(AccountType.SELLER, user.loginId))

    fun createToken(user: ScopedLoginDto): TokenEntry = createToken(AuthScope(user))

    fun createToken(scope: AuthScope): TokenEntry {
        val scopeString = json.encodeToString(scope)

        val claims = Jwts.claims().setSubject(scopeString)
        val now = Date()
        val validity = Date(now.time + validDurationInSeconds * 1000)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact().let { token ->
                TokenEntry(
                    authScope = scope,
                    token = token,
                    expiresAt = validity,
                    createdAt = now
                ).also { entry ->
                    tokens[entry.token] = entry
                }
            }
    }

    fun getSubject(token: String): AuthScope? = try {
        val subject = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
        json.decodeFromString<AuthScope>(subject)
    } catch (e: JwtException) {
        null
    }

    fun validateToken(token: String): AuthScope {
        val subject = getSubject(token) ?: throw ReportableError("유효하지 않은 토큰입니다.", HttpStatus.INTERNAL_SERVER_ERROR)
        val tokenEntry = tokens[token] ?: throw ReportableError("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED)
        if (tokenEntry.authScope != subject) throw ReportableError("데이터가 변형된 토큰입니다.", HttpStatus.UNAUTHORIZED)
        else if (tokenEntry.isValid()) throw ReportableError("이미 만료된 토큰입니다.", HttpStatus.UNAUTHORIZED)

        return subject
    }

    @Serializable
    data class AuthScope(
        val type: AccountType,
        val id: String,
    ) {
        constructor(scopedDto: ScopedLoginDto) : this(scopedDto.scope, scopedDto.loginId)
    }

    data class TokenEntry(
        val authScope: AuthScope,
        val token: String,
        val createdAt: Date,
        val expiresAt: Date,
    ) {
        fun isValid() = Date() in createdAt..expiresAt
    }
}
