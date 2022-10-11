package com.example.demo

import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.context.WebApplicationContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.setup.MockMvcBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

@SpringBootTest
@RunWith(SpringRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DemoApplicationTests {
	private val baseUrl = "http://localhost:8081/person/"
	private val jsonContentType = MediaType(MediaType.APPLICATION_JSON.type, MediaType.APPLICATION_JSON.subtype)
	private lateinit var mockMvc: MockMvc
	companion object { var id: Long = 0 }

	@Autowired
	private lateinit var webAppContext: WebApplicationContext

	@Before
	fun before() {
		mockMvc = webAppContextSetup(webAppContext).build()
	}

	@Test
	fun `1 - Get list of persons`() {
		val request = get(baseUrl).contentType(jsonContentType)

		mockMvc.perform(request)
			.andExpect(status().isOk)
	}
	// Далее по аналогии
	@Test
	fun `2 - Add new person`() {
		val passedJsonString = """
            {	
                "name": "Vanya",
                "lastName": "Ivanov"
            }
        """.trimIndent()

		val request = post(baseUrl).contentType(jsonContentType).content(passedJsonString)

		val resAction = mockMvc.perform(request)
		id = resAction.andReturn().response.contentAsString.toLong()
//		val resultJsonString = """
//            {
//				"id": $id,
//                "name": "Vanya",
//                "lastName": "Ivanov"
//
//            }
//        """.trimIndent()

		resAction.andExpect(status().isCreated)
	}

	@Test
	fun `3 - Update new person`() {
		val passedJsonString = """
            {
                "name": "Vanya",
                "lastName": "Smirnov"
            }
        """.trimIndent()

		val request = put(baseUrl + "$id").contentType(jsonContentType).content(passedJsonString)

		val resultJsonString = """
            {
				"id": $id,
                "name": "Vanya",
                "lastName": "Smirnov"
            }
        """.trimIndent()

		mockMvc.perform(request)
			.andExpect(status().isOk).andExpect(content().string("1"))
	}

	@Test
	fun `4 - Get new person`() {
		val request = get(baseUrl + "$id").contentType(jsonContentType)

		val resultJsonString = """
            {
				"id": $id,
                "name": "Vanya",
                "lastName": "Smirnov"
            }
        """.trimIndent()

		mockMvc.perform(request)
			.andExpect(status().isFound)
			.andExpect(content().json(resultJsonString, true))
	}

	fun addEqualsNames() {
		for(i in 1..3) {
			mockMvc.perform(post(baseUrl).contentType(jsonContentType).content("""				
			{
				"name": "Vanya150",
				"lastName": "Smirnov15$i"
			}""".trimIndent()))
		}
	}

	@Test
	fun `5 - Get all by name`() {
		val request = get(baseUrl + "name/Vanya150").contentType(jsonContentType)

		addEqualsNames()

		val resultJsonString = """
			[
            {
				"id": ${id + 1},
                "name": "Vanya150",
                "lastName": "Smirnov151"
            },
            {
				"id": ${id + 2},
                "name": "Vanya150",
                "lastName": "Smirnov152"
            },
            {
				"id": ${id + 3},
                "name": "Vanya150",
                "lastName": "Smirnov153"
            }
			]
        """.trimIndent()

		mockMvc.perform(request)
			.andExpect(status().isFound)
			.andExpect(content().json(resultJsonString, true))
	}

	fun addEqualsLastNames() {
		for(i in 1..3) {
			mockMvc.perform(post(baseUrl).contentType(jsonContentType).content("""				
			{
				"name": "Vanya15$i",
				"lastName": "Smirnov150"
			}""".trimIndent()))
		}
	}

	@Test
	fun `6 - Get all by last name`() {
		val request = get(baseUrl + "lastName/Smirnov150").contentType(jsonContentType)

		addEqualsLastNames()

		val resultJsonString = """
			[
            {
				"id": ${id + 4},
                "name": "Vanya151",
                "lastName": "Smirnov150"
            },
            {
				"id": ${id + 5},
                "name": "Vanya152",
                "lastName": "Smirnov150"
            },
            {
				"id": ${id + 6},
                "name": "Vanya153",
                "lastName": "Smirnov150"
            }
			]
        """.trimIndent()

		mockMvc.perform(request)
			.andExpect(status().isFound)
			.andExpect(content().json(resultJsonString, true))
	}

	fun erase() {
		for(i in 1..6) {
			mockMvc.perform(delete(baseUrl + "${id + i} ").contentType(jsonContentType))
		}
	}

	@Test
	fun `7 - Delete new person`() {
		erase()
		val request = delete(baseUrl + "$id").contentType(jsonContentType)

		mockMvc.perform(request)
			.andExpect(status().isOk)
			.andExpect(content().string("1"))
	}

}