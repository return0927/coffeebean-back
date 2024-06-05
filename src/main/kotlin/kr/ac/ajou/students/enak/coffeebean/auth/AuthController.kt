package kr.ac.ajou.students.enak.coffeebean.auth

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
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
import javax.servlet.http.HttpServletRequest

@Api(tags = ["1. 인증"])
@RestController
@RequestMapping("/api/")
class AuthController(
    private val customerService: CustomerService,
    private val producerService: SellerService,
) {
    @ApiOperation("회원가입 - 소비자")
    @PutMapping("/register/customer")
    fun putCustomer(@RequestBody dto: NewCustomerDto): SavedCustomerDto {
        return customerService.handleRegister(dto)
    }

    @ApiOperation("회원가입 - 판매자")
    @PutMapping("/register/producer")
    fun putProducer(@RequestBody dto: NewSellerDto): SavedSellerDto {
        return producerService.handleRegister(dto)
    }

    @ApiOperation("로그인 - 소비자")
    @PostMapping("/login/customer")
    fun loginCustomer(@RequestBody dto: LoginDto): SavedCustomerDto {
        return customerService.handleLogin(dto)
    }

    @ApiOperation("로그인 - 생산자")
    @PostMapping("/login/producer")
    fun loginProducer(@RequestBody dto: LoginDto): SavedSellerDto {
        return producerService.handleLogin(dto)
    }

    @ApiOperation("내 정보 가져오기")
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