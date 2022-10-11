package com.example.demo.controller

import com.example.demo.dto.PersonDto
import com.example.demo.service.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("person")
class PersonController(private val personService: PersonService) {

    @GetMapping
    fun getAll() = personService.getAll()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody person: PersonDto) : Long = personService.create(person)

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun getById(@PathVariable id: Long) = personService.getById(id)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody person: PersonDto) = personService.edit(id, person)

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long) = personService.remove(id)

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    fun getAllByName(@PathVariable name: String) = personService.getAllWithName(name)

    @GetMapping("/lastName/{lastName}")
    @ResponseStatus(HttpStatus.FOUND)
    fun getAllByLastName(@PathVariable lastName: String) = personService.getAllWithLastName(lastName)

}