package com.example.demo.model

import javax.persistence.Id
import javax.persistence.Column
import javax.persistence.Table
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
@Table(name = "person")
data class Person(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "name", length = 100)
    val name: String = "",

    @Column(name = "last_name", length = 100)
    val lastName: String = ""
)