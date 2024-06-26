package kr.ac.ajou.students.enak.coffeebean.customer

import kr.ac.ajou.students.enak.coffeebean.auth.AuthService
import kr.ac.ajou.students.enak.coffeebean.auth.dto.LoginDto
import kr.ac.ajou.students.enak.coffeebean.auth.dto.NewCustomerDto
import kr.ac.ajou.students.enak.coffeebean.errors.ReportingError
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.util.*

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val authService: AuthService,
) {
    private fun hashPassword(password: String): ByteArray {
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(password.toByteArray())
        return digest.digest()
    }

    fun handleRegister(dto: NewCustomerDto): SavedCustomerDto {
        val user = customerRepository.findUserByLoginId(dto.loginId)
            ?.run { throw ReportingError("이미 가입된 아이디입니다.", HttpStatus.BAD_REQUEST) }
            ?: CustomerEntity(
                loginId = dto.loginId,
                pw = hashPassword(dto.password),
                firstName = dto.firstName,
                lastName = dto.lastName,
                gender = dto.gender,
                registrationDate = Date(),
                birthday = dto.birthDate.let { date ->
                    date.split("-").map { it.toInt() }
                }.let { Date(it[0], it[1], it[2]) },
                contacts = dto.phone,
            )

        user.markDirty()
        val saved = customerRepository.insert(user)
        val token = authService.createToken(saved)
        return SavedCustomerDto(saved, token)
    }

    fun handleLogin(dto: LoginDto): SavedCustomerDto {
        val user = customerRepository.findUserByLoginId(dto.loginId)
            ?: throw ReportingError("아이디가 존재하지 않거나 비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST)

        if (!user.pw.contentEquals(hashPassword(dto.password)))
            throw ReportingError("아이디가 존재하지 않거나 비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST)

        val token = authService.createToken(user)
        return SavedCustomerDto(user, token)
    }

    fun getViaLogin(loginId: String): CustomerEntity? {
        return customerRepository.findUserByLoginId(loginId)
    }
}
