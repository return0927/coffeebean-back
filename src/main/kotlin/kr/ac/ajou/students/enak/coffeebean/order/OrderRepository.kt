package kr.ac.ajou.students.enak.coffeebean.order

import kr.ac.ajou.students.enak.coffeebean.abc.Repository
import kr.ac.ajou.students.enak.coffeebean.errors.ReportingError
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

    fun findBySellerId(id: Long): List<OrderEntity> {
        return query(
            "SELECT orders.* " +
                    "FROM orders, products, producers " +
                    "WHERE orders.item_id = products.id " +
                    "  AND products.brand_name = producers.company_name" +
                    "  AND producers.id = ?;", id
        ) { rs ->
            return@query OrderEntity(rs)
        }.filterNotNull()
    }

    fun saveNewOrder(order: OrderEntity): OrderEntity {
        val fetched = query(
            "INSERT INTO orders " +
                    "(item_id, customer_id, order_date, price, amount, deliver_address, recipient, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                    "RETURNING *;",
            order.itemId,
            order.customerId,
            order.orderDate,
            order.price,
            order.amount,
            order.deliverAddress,
            order.recipient,
            order.status.name,
        ) { rs ->
            return@query OrderEntity(rs)
        }.firstOrNull()

        fetched ?: throw ReportingError("$order (을)를 저장하는데 오류가 발생했습니다.")
        return order.copyFrom(fetched)
    }
}
