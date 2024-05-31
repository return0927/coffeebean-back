package kr.ac.ajou.students.enak.coffeebean.auth.entity

import kr.ac.ajou.students.enak.coffeebean.abc.Entity
import java.sql.ResultSet

data class CustomerEntity(
    val id: Int,
    val pw: ByteArray,
    val firstName: String,
    val lastName: String,
    val gender: Int,
    val registrationDate: java.sql.Date,
    val birthday: java.sql.Date,
    val contacts: String,
) : Entity {
    constructor(rs: ResultSet) : this(
        id = rs.getInt("id"),
        pw = rs.getBytes("pw"),
        firstName = rs.getString("firstName"),
        lastName = rs.getString("lastName"),
        gender = rs.getInt("gender"),
        registrationDate = rs.getDate("registrationDate"),
        birthday = rs.getDate("birthday"),
        contacts = rs.getString("contacts"),
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CustomerEntity

        if (id != other.id) return false
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
        var result = id
        result = 31 * result + pw.contentHashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + gender
        result = 31 * result + registrationDate.hashCode()
        result = 31 * result + birthday.hashCode()
        result = 31 * result + contacts.hashCode()
        return result
    }
}
