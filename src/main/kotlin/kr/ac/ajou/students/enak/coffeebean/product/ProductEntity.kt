package kr.ac.ajou.students.enak.coffeebean.product

import kr.ac.ajou.students.enak.coffeebean.abc.Entity
import java.sql.ResultSet

data class ProductEntity(
    val id: Long,
    var name: String,
    var brandName: String,
    var origins: String? = null,
    var quantity: Long,
    var processing: String? = null,
    var grinding: String? = null,
    var price: Long,
    var discounts: Long,
    var imageUrl: String,
) : Entity {
    private val lastSyncedMatrix: MutableMap<String, Any> = hashMapOf()

    var isDirty: Boolean = false
        private set

    override fun getLastSyncedMatrix(): MutableMap<String, Any> = this.lastSyncedMatrix

    override fun markDirty() {
        this.isDirty = true
    }

    constructor(rs: ResultSet) : this(
        id = rs.getLong("id"),
        name = rs.getString("name"),
        brandName = rs.getString("brand_name"),
        origins = rs.getString("origins"),
        quantity = rs.getLong("quantity"),
        processing = rs.getString("processing"),
        grinding = rs.getString("grinding"),
        price = rs.getLong("price"),
        discounts = rs.getLong("discounts"),
        imageUrl = rs.getString("image_url") ?: "",
    )
}
