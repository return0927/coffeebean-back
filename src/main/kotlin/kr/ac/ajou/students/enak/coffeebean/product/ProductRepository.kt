package kr.ac.ajou.students.enak.coffeebean.product

import kr.ac.ajou.students.enak.coffeebean.abc.Repository
import org.springframework.stereotype.Component

@Component
class ProductRepository : Repository<ProductEntity>() {
    fun getProductById(id: Long): ProductEntity? {
        val result = query("SELECT * FROM products WHERE id = ?", id) { rs ->
            return@query ProductEntity(
                id = rs.getLong("id"),
                brandName = rs.getString("brand_name"),
                origins = rs.getString("origins"),
                quantity = rs.getLong("quantity"),
                processing = rs.getString("processing"),
                grinding = rs.getString("grinding"),
                price = rs.getLong("price"),
                discounts = rs.getLong("discounts"),
            )
        }

        return result.firstOrNull()
    }
}