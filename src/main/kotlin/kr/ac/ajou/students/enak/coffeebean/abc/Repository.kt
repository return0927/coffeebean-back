package kr.ac.ajou.students.enak.coffeebean.abc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import java.sql.ResultSet

abstract class Repository<E : Entity> {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    companion object {
        fun <T> wrapMapper(parser: (rs: ResultSet) -> T?) = RowMapper<T> { rs: ResultSet, _: Int ->
            return@RowMapper parser.invoke(rs)
        }
    }

    protected fun <T> query(query: String, vararg params: Any, parser: (rs: ResultSet) -> T?): List<T?> {
        val result = jdbcTemplate.query(query, wrapMapper(parser), *params)
        return result
    }

    protected fun <T> query(query: String, params: SqlParameterSource, parser: (rs: ResultSet) -> T?): List<T?> {
        val result = jdbcTemplate.query(query, wrapMapper(parser), params)
        return result
    }

    protected fun <T> queryForList(query: String, vararg params: Any, clazz: Class<T>): List<T> {
        return jdbcTemplate.queryForList(query, clazz, *params)
    }
}