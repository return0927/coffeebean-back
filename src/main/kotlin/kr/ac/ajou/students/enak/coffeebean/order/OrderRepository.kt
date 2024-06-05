package kr.ac.ajou.students.enak.coffeebean.order

import kr.ac.ajou.students.enak.coffeebean.abc.Repository
import kr.ac.ajou.students.enak.coffeebean.errors.ReportingError
import org.springframework.http.HttpStatus
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

    fun updateOrder(orderId: Int, order: UpdateOrderDto): OrderEntity {
        val updating = listOf(
            "status = ?" to order.status?.name,
            "deliver_address = ?" to order.deliverAddress,
            "recipient = ?" to order.recipient,
        ).filter { it.second != null }

        val queries = mutableListOf<String>()
        val arguments = mutableListOf<Any>()

        updating.forEach { (query, arg) ->
            queries.add(query)
            arguments.add(arg!!)
        }

        query(
            "UPDATE orders " +
                    "SET " + queries.joinToString(" , ") + " " +
                    "WHERE id = ?;",
            *arguments.toTypedArray(),
            orderId,
        ) {
            return@query OrderEntity(it)
        }

        val fetched = findByOrderId(orderId)
            ?: throw ReportingError("주문 $orderId (을)를 찾지 못했습니다.", HttpStatus.NOT_FOUND)
        return fetched
    }
}
