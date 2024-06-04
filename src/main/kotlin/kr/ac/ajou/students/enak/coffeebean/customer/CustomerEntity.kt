package kr.ac.ajou.students.enak.coffeebean.customer

import kr.ac.ajou.students.enak.coffeebean.abc.Entity
import java.sql.ResultSet
import java.util.*


data class CustomerEntity(
    var id: Int? = null,
    var loginId: String,
    var pw: ByteArray,
    var firstName: String,
    var lastName: String,
    var gender: Int,
    var registrationDate: Date,
    var birthday: Date,
    var contacts: String,
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
        loginId = rs.getString("login_id"),
        pw = rs.getBytes("pw"),
        firstName = rs.getString("first_name"),
        lastName = rs.getString("last_name"),
        gender = rs.getInt("gender"),
        registrationDate = rs.getDate("registration_date"),
        birthday = rs.getDate("birthday"),
        contacts = rs.getString("contacts"),
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CustomerEntity

        if (id != other.id) return false
        if (loginId != other.loginId) return false
        if (!pw.contentEquals(other.pw)) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (gender != other.gender) return false
        if (registrationDate != other.registrationDate) return false
        if (birthday != other.birthday) return false
        if (contacts != other.contacts) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + loginId.hashCode()
        result = 31 * result + pw.contentHashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + gender
        result = 31 * result + registrationDate.hashCode()
        result = 31 * result + birthday.hashCode()
        result = 31 * result + contacts.hashCode()
        return result
    }

    fun copyFrom(other: CustomerEntity): CustomerEntity {
        this.id = other.id
        this.loginId = other.loginId
        this.pw = other.pw
        this.firstName = other.firstName
        this.lastName = other.lastName
        this.gender = other.gender
        this.registrationDate = other.registrationDate
        this.birthday = other.birthday
        this.contacts = other.contacts
        return this
    }

    fun toDto() = CustomerDto(
        id = this.id!!,
        firstName = this.firstName,
        lastName = this.lastName,
    )
}