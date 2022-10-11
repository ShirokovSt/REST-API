package com.example.demo.service

import com.example.demo.dto.PersonDto
import com.example.demo.model.Person
import com.example.demo.repository.PersonRepository
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class PersonServiceImpl(
     private val personRepository: PersonRepository
) : PersonService {
    override fun getAll(): List<PersonDto> = personRepository.getAll()
        .map{it.toDto()}

    override fun getById(id: Long): PersonDto = personRepository.getById(id)
        ?.toDto()
        ?: throw RuntimeException("Person with $id not found")

    override fun create(person: PersonDto) : Long = personRepository.create(person.name, person.lastName)


    override fun edit(id: Long, person: PersonDto) : Int =
        personRepository.edit(id, person.name, person.lastName)

    override fun remove(id: Long) : Int =
        personRepository.remove(id)

    override fun getAllWithName(name: String) : List<Person> =
        personRepository.getAllWithName(name)

    override fun getAllWithLastName(lastName: String) : List<Person> =
        personRepository.getAllWithLastName(lastName)

    private fun Person.toDto() = PersonDto(
        id = id,
        name = name,
        lastName = lastName
    )
}