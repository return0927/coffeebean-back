package kr.ac.ajou.students.enak.coffeebean.auth

import kr.ac.ajou.students.enak.coffeebean.auth.dto.NewCustomerDto
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/")
class AuthController(
    private val authService: AuthService,
) {
    @PutMapping("/register/customer")
    fun putCustomer(@RequestBody dto: NewCustomerDto): String {
        println(dto)
        return "Hi, ${dto.lastName} ${dto.firstName}"
    }
}