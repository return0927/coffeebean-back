package kr.ac.ajou.students.enak.coffeebean.auth

import kr.ac.ajou.students.enak.coffeebean.auth.dto.NewCustomerDto
import kr.ac.ajou.students.enak.coffeebean.auth.dto.NewSellerDto
import kr.ac.ajou.students.enak.coffeebean.customer.CustomerService
import kr.ac.ajou.students.enak.coffeebean.customer.SavedCustomerDto
import kr.ac.ajou.students.enak.coffeebean.seller.SavedSellerDto
import kr.ac.ajou.students.enak.coffeebean.seller.SellerService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}