package kr.ac.ajou.students.enak.coffeebean.seller

import kr.ac.ajou.students.enak.coffeebean.auth.AuthService
import kr.ac.ajou.students.enak.coffeebean.auth.dto.NewSellerDto
import kr.ac.ajou.students.enak.coffeebean.errors.ReportingError
import kr.ac.ajou.students.enak.coffeebean.product.ProductDto
import kr.ac.ajou.students.enak.coffeebean.product.ProductService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.security.MessageDigest
import kotlin.math.min

@Service
class SellerService(
    private val repository: SellerRepository,
    private val productService: ProductService,
    private val authService: AuthService,
) {
    private fun hashPassword(password: String): ByteArray {
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(password.toByteArray())
        return digest.digest()
    }

    fun listSellerBrief(size: Int = 100): List<SellerBriefDto> {
        return repository.listSellers(min(size, 100)).map {
            it.toBriefDto()
        }
    }

    fun getById(id: Long): SellerDto? {
        val result = repository.getById(id)

        return result?.toDto(
            productService.listProductsBySeller(result)
        )
    }

    fun getByName(name: String): SellerDto? {
        val result = repository.getByName(name)
        return result?.toDto(
            productService.listProductsBySeller(result)
        )
    }

    fun handleRegister(dto: NewSellerDto): SavedSellerDto {
        val user = repository.findByLoginId(dto.loginId)
            ?.run { throw ReportingError("이미 가입된 아이디입니다.", HttpStatus.BAD_REQUEST) }
            ?: SellerEntity(
                loginId = dto.loginId,
                pw = hashPassword(dto.password),
                companyName = dto.companyName,
                companyRegistrationNumber = dto.companyRegistrationNumber,
                businessAddress = dto.businessAddress
            )

        user.markDirty()
        val saved = repository.insert(user)
        val token = authService.createToken(saved)
        return SavedSellerDto(saved, token)
    }

    private fun SellerEntity.toBriefDto(): SellerBriefDto = SellerBriefDto(
        id = this.id!!,
        companyName = this.companyName,
        businessAddress = this.businessAddress,
    )

    private fun SellerEntity.toDto(
        products: List<ProductDto>,
    ): SellerDto = SellerDto(
        id = this.id!!,
        companyName = this.companyName,
        companyRegistrationNumber = this.companyRegistrationNumber,
        businessAddress = this.businessAddress,
        products = products,
    )
}
