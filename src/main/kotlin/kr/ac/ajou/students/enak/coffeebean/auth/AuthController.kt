package kr.ac.ajou.students.enak.coffeebean.auth

import kr.ac.ajou.students.enak.coffeebean.AccountType
import kr.ac.ajou.students.enak.coffeebean.auth.dto.LoginDto
import kr.ac.ajou.students.enak.coffeebean.auth.dto.NewCustomerDto
import kr.ac.ajou.students.enak.coffeebean.auth.dto.NewSellerDto
import kr.ac.ajou.students.enak.coffeebean.customer.CustomerEntity
import kr.ac.ajou.students.enak.coffeebean.customer.CustomerService
import kr.ac.ajou.students.enak.coffeebean.customer.SavedCustomerDto
import kr.ac.ajou.students.enak.coffeebean.errors.AuthRequiredError
import kr.ac.ajou.students.enak.coffeebean.seller.SavedSellerDto
import kr.ac.ajou.students.enak.coffeebean.seller.SellerEntity
import kr.ac.ajou.students.enak.coffeebean.seller.SellerService
import org.springframework.web.bind.annotation.*
import springfox.documentation.schema.property.bean.AccessorsProvider
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/")
class AuthController(
    private val authService: AuthService,
    private val customerService: CustomerService,
    private val producerService: SellerService,
    private val accessorsProvider: AccessorsProvider,
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

    @GetMapping("/me")
    @AuthRequired
    fun whoAmI(req: HttpServletRequest): Any {
        val accType = req.getUserType() ?: throw AuthRequiredError()

        when (accType) {
            AccountType.SELLER -> {
                val entity = req.getUser<SellerEntity>()
                return entity.toBriefDto()
            }

            AccountType.CUSTOMER -> {
                val entity = req.getUser<CustomerEntity>()
                return entity.toDto()
            }
        }
    }
}