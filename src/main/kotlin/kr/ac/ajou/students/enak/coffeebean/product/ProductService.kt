package kr.ac.ajou.students.enak.coffeebean.product

import kr.ac.ajou.students.enak.coffeebean.errors.ReportingError
import kr.ac.ajou.students.enak.coffeebean.seller.SellerEntity
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import kotlin.math.min

@Service
class ProductService(
    private val repository: ProductRepository,
) {
    fun listProducts(
        size: Int,
        kind: String?,
        amount: Int?,
        origin: String?,
        sort: ProductController.Companion.ProductSortMethod,
    ): List<ProductDto> {
        return repository.listProducts(min(size, 100), kind, amount, origin, sort).map {
            it.toDto()
        }
    }

    fun searchByProductId(id: Int): ProductDto? {
        return repository.getProductById(id)?.toDto()
    }

    fun listProductsBySeller(seller: SellerEntity): List<ProductDto> {
        val name = seller.companyName

        return repository.listProductsBySellerName(name).map {
            it.toDto()
        }
    }

    private fun ProductEntity.toDto(): ProductDto {
        return ProductDto(
            productId = id!!,
            name = name,
            brandName = brandName,
            origins = origins,
            quantity = quantity,
            processing = processing,
            grinding = grinding,
            price = price,
            discounts = discounts,
            imageUrl = imageUrl,
        )
    }

    fun createNewProduct(seller: SellerEntity, dto: NewProductDto): ProductDto {
        val product = ProductEntity(
            name = dto.name,
            brandName = seller.companyName,
            origins = dto.origins,
            quantity = dto.quantity,
            processing = dto.processing,
            grinding = dto.grinding,
            price = dto.price,
            discounts = dto.discounts,
            imageUrl = dto.imageUrl,
        )
        return repository.createNewEntity(product).toDto()
    }

    fun updateProduct(seller: SellerEntity, id: Int, dto: UpdateProductDto): ProductDto {
        val product = repository.getProductById(id)
            ?: throw ReportingError("상품 $id (을)를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        if (product.brandName != seller.companyName)
            throw ReportingError("해당 상품을 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN)

        if (dto.name != null) product.name = dto.name
        if (dto.origins != null) product.origins = dto.origins
        if (dto.quantity != null) product.quantity = dto.quantity
        if (dto.processing != null) product.processing = dto.processing
        if (dto.grinding != null) product.grinding = dto.grinding
        if (dto.price != null) product.price = dto.price
        if (dto.discounts != null) product.discounts = dto.discounts
        if (dto.imageUrl != null) product.imageUrl = dto.imageUrl

        return repository.updateProduct(product).toDto()

    }
}
