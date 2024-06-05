package kr.ac.ajou.students.enak.coffeebean.order

import kr.ac.ajou.students.enak.coffeebean.abc.Entity
import java.sql.ResultSet
import java.util.*

data class OrderEntity(
    var id: Int? = null,
    var itemId: Int,
    var customerId: Int,
    var orderDate: Date,
    var price: Long,
    var amount: Int,
    var deliverAddress: String,
    var recipient: String,
    var status: OrderStatus,
) : Entity {
    private val lastSyncedMatrix: MutableMap<String, Any> = hashMapOf()

    var isDirty: Boolean = false
        private set

    override fun getLastSyncedMatrix(): MutableMap<String, Any> = this.lastSyncedMatrix

    override fun markDirty() {
        this.isDirty = true
    }

    constructor(rs: ResultSet) : this(
        id = rs.getInt("id"),
        itemId = rs.getInt("item_id"),
        customerId = rs.getInt("customer_id"),
        orderDate = rs.getDate("order_date"),
        price = rs.getLong("price"),
        amount = rs.getInt("amount"),
        deliverAddress = rs.getString("deliver_address"),
        recipient = rs.getString("recipient"),
        status = OrderStatus[rs.getString("status")],
    )
}
