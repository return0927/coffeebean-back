package kr.ac.ajou.students.enak.coffeebean.seller

import kr.ac.ajou.students.enak.coffeebean.product.ProductDto
import kr.ac.ajou.students.enak.coffeebean.product.ProductService
import org.springframework.stereotype.Service
import kotlin.math.min

@Service
class SellerService(
    private val repository: SellerRepository,
    private val productService: ProductService,
) {
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
