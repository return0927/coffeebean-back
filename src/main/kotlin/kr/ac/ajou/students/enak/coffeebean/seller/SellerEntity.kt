package kr.ac.ajou.students.enak.coffeebean.seller

import kr.ac.ajou.students.enak.coffeebean.abc.Entity
import kr.ac.ajou.students.enak.coffeebean.product.ProductDto
import java.sql.ResultSet

data class SellerEntity(
    var id: Long? = null,
    var loginId: String,
    var pw: ByteArray,
    var companyName: String,
    var companyRegistrationNumber: Long,
    var businessAddress: String,
) : Entity {
    private val lastSyncedMatrix: MutableMap<String, Any> = hashMapOf()

    var isDirty: Boolean = false
        private set

    override fun getLastSyncedMatrix(): MutableMap<String, Any> = this.lastSyncedMatrix

    override fun markDirty() {
        this.isDirty = true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SellerEntity

        if (id != other.id) return false
        if (loginId != other.loginId) return false
        if (!pw.contentEquals(other.pw)) return false
        if (companyName != other.companyName) return false
        if (companyRegistrationNumber != other.companyRegistrationNumber) return false
        if (businessAddress != other.businessAddress) return false
        if (lastSyncedMatrix != other.lastSyncedMatrix) return false
        if (isDirty != other.isDirty) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + loginId.hashCode()
        result = 31 * result + pw.contentHashCode()
        result = 31 * result + companyName.hashCode()
        result = 31 * result + companyRegistrationNumber.hashCode()
        result = 31 * result + businessAddress.hashCode()
        result = 31 * result + lastSyncedMatrix.hashCode()
        result = 31 * result + isDirty.hashCode()
        return result
    }

    fun copyFrom(fetched: SellerEntity): SellerEntity {
        this.id = fetched.id
        this.loginId = fetched.loginId
        this.pw = fetched.pw
        this.companyName = fetched.companyName
        this.companyRegistrationNumber = fetched.companyRegistrationNumber
        this.businessAddress = fetched.businessAddress
        return this
    }

    constructor(rs: ResultSet) : this(
        id = rs.getLong("id"),
        loginId = rs.getString("login_id"),
        pw = rs.getBytes("pw"),
        companyName = rs.getString("company_name"),
        companyRegistrationNumber = rs.getLong("company_registration_number"),
        businessAddress = rs.getString("business_address"),
    )

    fun toDto(
        products: List<ProductDto>,
    ): SellerDto = SellerDto(
        id = this.id!!,
        companyName = this.companyName,
        companyRegistrationNumber = this.companyRegistrationNumber,
        businessAddress = this.businessAddress,
        products = products,
    )

    fun toBriefDto(): SellerBriefDto = SellerBriefDto(
        id = this.id!!,
        companyName = this.companyName,
        businessAddress = this.businessAddress,
    )
}