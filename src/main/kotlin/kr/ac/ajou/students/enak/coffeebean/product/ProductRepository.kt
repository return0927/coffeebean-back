package kr.ac.ajou.students.enak.coffeebean.product

import kr.ac.ajou.students.enak.coffeebean.abc.Repository
import org.springframework.stereotype.Component

@Component
class ProductRepository : Repository<ProductEntity>() {
    fun getProductById(id: Long): ProductEntity? {
        val result = query("SELECT * FROM products WHERE id = ?", id) { rs ->
            return@query ProductEntity(rs)
        }

        return result.firstOrNull()
    }
}