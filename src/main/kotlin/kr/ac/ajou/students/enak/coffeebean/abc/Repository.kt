package kr.ac.ajou.students.enak.coffeebean.abc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

abstract class Repository<T : Entity> {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    protected fun query(query: String, vararg params: Any, parser: (rs: ResultSet) -> T?): List<T?> {
        val result = jdbcTemplate.query(query, RowMapper { rs, rowNum ->
            return@RowMapper parser(rs)
        }, *params)
        return result
    }

    protected fun <T> queryForList(query: String, vararg params: Any, clazz: Class<T>): List<T> {
        return jdbcTemplate.queryForList(query, clazz, *params)
    }
}