package kr.ac.ajou.students.enak.coffeebean.product

import org.springframework.stereotype.Service
import kotlin.math.min

@Service
class ProductService(
    private val repository: ProductRepository,
) {
    fun listProducts(size: Int): List<ProductDto> {
        return repository.listProducts(min(size, 100)).map {
            it.toDto()
        }
    }

    fun searchByProductId(id: Long): ProductDto? {
        return repository.getProductById(id)?.toDto()
    }

    private fun ProductEntity.toDto(): ProductDto {
        return ProductDto(
            productId = id,
            brandName = brandName,
            origins = origins,
            quantity = quantity,
            processing = processing,
            grinding = grinding,
            price = price,
            discounts = discounts,
        )
    }
}
