package com.example.demo.repository

import com.example.demo.model.Person

interface PersonRepository {
    fun getAll(): List<Person>

    fun getById(id: Long): Person?

    fun create(name: String, lastName: String) : Long

    fun edit(id: Long, name: String, lastName: String) : Int

    fun remove(id: Long) : Int

    fun getAllWithName(name: String) : List<Person>

    fun getAllWithLastName(lastName: String) : List<Person>
}