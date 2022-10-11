package com.example.demo.repository

import com.example.demo.model.Person
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class PersonRepositoryImpl (
    private val jdbcTemplate: NamedParameterJdbcTemplate
        ): PersonRepository {
    override fun getAll(): List<Person>  =
        jdbcTemplate.query(
            "select * from person order by name",
            ROW_MAPPER
        )

    override fun getById(id: Long): Person? =
        jdbcTemplate.query(
            "select * from person where id = :id",
            mapOf(
                "id" to id,
            ),
            ROW_MAPPER
        ).firstOrNull()

    override fun create(name: String, lastName: String): Long {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            "insert into person (name, last_name) values (:name, :lastName)",
            MapSqlParameterSource(
                mapOf(
                    "name" to name,
                    "lastName" to lastName
                )
            ),
            keyHolder,
            listOf("id").toTypedArray()
        )
        return keyHolder.keys?.getValue("GENERATED_KEY").toString().toLong()
    }

    override fun edit(id: Long, name: String, lastName: String): Int =
        jdbcTemplate.update(
            "update person set name = :name, last_name = :lastName where id = :id",
            mapOf(
                "id" to id,
                "name" to name,
                "lastName" to lastName
            ),
        )

    override fun remove(id: Long): Int =
        jdbcTemplate.update(
            "delete from person where id = :id",
            mapOf(
                "id" to id,
            )
        )

    override fun getAllWithName(name: String) : List<Person> =
        jdbcTemplate.query(
            "select * from person where name = :name",
            mapOf(
                "name" to name,
            ),
            ROW_MAPPER
        )

    override fun getAllWithLastName(lastName: String) : List<Person> =
        jdbcTemplate.query(
        "select * from person where last_name = :lastName",
        mapOf(
            "lastName" to lastName,
        ),
        ROW_MAPPER
    )

    private companion object {
        val ROW_MAPPER = RowMapper<Person> { rs, _ ->
            Person (
                id = rs.getLong("id"),
                name = rs.getString("name"),
                lastName = rs.getString("last_name")
            )
        }
    }
}