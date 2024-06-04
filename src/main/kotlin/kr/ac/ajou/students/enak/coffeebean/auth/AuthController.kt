package kr.ac.ajou.students.enak.coffeebean.auth

import kr.ac.ajou.students.enak.coffeebean.auth.dto.NewCustomerDto
import kr.ac.ajou.students.enak.coffeebean.customer.CustomerService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/")
class AuthController(
    private val authService: AuthService,
    private val customerService: CustomerService,
) {
    @PutMapping("/register/customer")
    fun putCustomer(@RequestBody dto: NewCustomerDto): Any {
        return customerService.handleRegister(dto)
    }
}