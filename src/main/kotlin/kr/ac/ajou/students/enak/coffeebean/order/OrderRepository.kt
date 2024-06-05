package kr.ac.ajou.students.enak.coffeebean.order

import kr.ac.ajou.students.enak.coffeebean.abc.Repository
import org.springframework.stereotype.Component

@Component
class OrderRepository : Repository<OrderEntity>() {
    fun findByOrderId(orderId: Int): OrderEntity? {
        return query("SELECT * FROM orders WHERE id = ? LIMIT 1;", orderId) {
            return@query OrderEntity(it)
        }.firstOrNull()
    }

    fun findByItemId(itemId: Int): List<OrderEntity> {
        return query("SELECT * FROM orders WHERE item_id = ?", itemId) {
            return@query OrderEntity(it)
        }.filterNotNull()
    }

    fun findByCustomerId(customerId: Int): List<OrderEntity> {
        return query("SELECT * FROM orders WHERE customer_id = ?", customerId) {
            return@query OrderEntity(it)
        }.filterNotNull()
    }
}
