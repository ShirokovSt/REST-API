package com.example.demo.service

import com.example.demo.dto.PersonDto

interface PersonService {
    fun getAll(): List<PersonDto>

    fun getById(id: Long): PersonDto

    fun create(person: PersonDto) : Long

    fun edit(id: Long, person: PersonDto) : Int

    fun remove(id: Long) : Int

    fun getAllWithName(name: String) : List<com.example.demo.model.Person>

    fun getAllWithLastName(lastName: String) : List<com.example.demo.model.Person>
}