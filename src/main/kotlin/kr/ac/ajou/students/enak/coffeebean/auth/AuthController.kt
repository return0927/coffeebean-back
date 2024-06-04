package kr.ac.ajou.students.enak.coffeebean.auth

import kr.ac.ajou.students.enak.coffeebean.auth.dto.LoginDto
import kr.ac.ajou.students.enak.coffeebean.auth.dto.NewCustomerDto
import kr.ac.ajou.students.enak.coffeebean.auth.dto.NewSellerDto
import kr.ac.ajou.students.enak.coffeebean.customer.CustomerService
import kr.ac.ajou.students.enak.coffeebean.customer.SavedCustomerDto
import kr.ac.ajou.students.enak.coffeebean.seller.SavedSellerDto
import kr.ac.ajou.students.enak.coffeebean.seller.SellerService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/")
class AuthController(
    private val authService: AuthService,
    private val customerService: CustomerService,
    private val producerService: SellerService,
) {
    @PutMapping("/register/customer")
    fun putCustomer(@RequestBody dto: NewCustomerDto): SavedCustomerDto {
        return customerService.handleRegister(dto)
    }

    @PutMapping("/register/producer")
    fun putProducer(@RequestBody dto: NewSellerDto): SavedSellerDto {
        return producerService.handleRegister(dto)
    }

    @PostMapping("/login/customer")
    fun loginCustomer(@RequestBody dto: LoginDto): SavedCustomerDto {
        return customerService.handleLogin(dto)
    }

    @PostMapping("/login/producer")
    fun loginProducer(@RequestBody dto: LoginDto): SavedSellerDto {
        return producerService.handleLogin(dto)
    }
}