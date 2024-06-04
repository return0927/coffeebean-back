package kr.ac.ajou.students.enak.coffeebean.customer

import kr.ac.ajou.students.enak.coffeebean.abc.Repository
import kr.ac.ajou.students.enak.coffeebean.errors.ReportingError
import org.springframework.stereotype.Component

@Component
class CustomerRepository : Repository<CustomerEntity>() {
    fun findUserByLoginId(loginId: String): CustomerEntity? {
        val result = query("SELECT * FROM customers WHERE login_id = ? LIMIT 1;", loginId) { rs ->
            return@query CustomerEntity(rs)
        }
        return result.firstOrNull()
    }

    fun insert(customer: CustomerEntity): CustomerEntity {
        val fetched = query(
            "INSERT INTO customers (login_id, pw, first_name, last_name, gender, registration_date, birthday, contacts) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                    "RETURNING *",
            customer.loginId,
            customer.pw,
            customer.firstName,
            customer.lastName,
            customer.gender,
            customer.registrationDate,
            customer.birthday,
            customer.contacts,
        ) { rs ->
            return@query CustomerEntity(rs)
        }.firstOrNull()

        fetched ?: throw ReportingError("$customer (을)를 저장하는데 오류가 발생했습니다.")
        return customer.copyFrom(fetched)
    }
}