package kr.ac.ajou.students.enak.coffeebean.product

import kr.ac.ajou.students.enak.coffeebean.abc.Repository
import kr.ac.ajou.students.enak.coffeebean.errors.ReportingError
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class ProductRepository : Repository<ProductEntity>() {
    fun listProducts(
        size: Int,
        kind: String?,
        amount: Int?,
        origin: String?,
        sort: ProductController.Companion.ProductSortMethod,
    ): List<ProductEntity> {
        val updating = listOf(
            "kind = ?" to kind,
            "quantity = ?" to amount,
            "origins = ?" to origin,
        ).filter { it.second != null }

        val queries = mutableListOf<String>()
        val arguments = mutableListOf<Any>()

        updating.forEach { (query, arg) ->
            queries.add(query)
            arguments.add(arg!!)
        }

        val subQuery = queries.joinToString(" and ")
        val sortQuery = when (sort) {
            ProductController.Companion.ProductSortMethod.NEWEST -> "ORDER BY id DESC"
            ProductController.Companion.ProductSortMethod.OLDEST -> "ORDER BY id ASC"
            ProductController.Companion.ProductSortMethod.LOWEST_PRICE -> "ORDER BY price ASC"
            ProductController.Companion.ProductSortMethod.HIGHEST_PRICE -> "ORDER BY price DESC"
            else -> throw ReportingError("지원하지 않는 정렬 방식 $sort", HttpStatus.BAD_REQUEST)
        }

        val result = query(
            "SELECT * " +
                    "FROM products " +
                    (if (subQuery.isNotEmpty()) "WHERE $subQuery" else "") + " " +
                    "$sortQuery LIMIT ?",
            *arguments.toTypedArray(),
            size
        ) {
            return@query ProductEntity(it)
        }
        return result.filterNotNull()
    }

    fun getProductById(id: Int): ProductEntity? {
        val result = query("SELECT * FROM products WHERE id = ?", id) { rs ->
            return@query ProductEntity(rs)
        }

        return result.firstOrNull()
    }

    fun listProductsBySellerName(brandName: String): List<ProductEntity> {
        val result = query("SELECT * FROM products WHERE brand_name = ?", brandName) { rs ->
            return@query ProductEntity(rs)
        }
        return result.filterNotNull()
    }

    fun createNewEntity(product: ProductEntity): ProductEntity {
        return query(
            "INSERT INTO products (name, brand_name, origins, quantity, processing, grinding, price, discounts, image_url) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                    "RETURNING *;",
            product.name,
            product.brandName,
            product.origins,
            product.quantity,
            product.processing,
            product.grinding,
            product.price,
            product.discounts,
            product.imageUrl,
        ) {
            return@query ProductEntity(it)
        }.firstOrNull() ?: throw ReportingError("상품 $product (을)를 저장하는데 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    fun updateProduct(product: ProductEntity): ProductEntity {
        query(
            "UPDATE products " +
                    "SET " +
                    "name = ? , " +
                    "origins = ? , " +
                    "quantity = ? , " +
                    "processing = ? , " +
                    "grinding = ? , " +
                    "price = ? , " +
                    "discounts = ? , " +
                    "image_url = ? " +
                    "WHERE id = ?;",
            product.name,
            product.origins,
            product.quantity,
            product.processing,
            product.grinding,
            product.price,
            product.discounts,
            product.imageUrl,
            product.id!!
        ) {
            return@query ProductEntity(it)
        }

        val fetched = getProductById(product.id!!)
            ?: throw ReportingError("상품 ${product.id} (을_를 찾지 못했습니다.")
        return fetched
    }
}