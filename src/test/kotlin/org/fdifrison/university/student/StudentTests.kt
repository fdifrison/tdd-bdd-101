package org.fdifrison.university.student

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.fdifrison.university.students.StudentController
import org.fdifrison.university.utils.IdGenerator
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.test.web.servlet.client.expectBody
import java.util.*

@WebMvcTest(controllers = [StudentController::class])
@AutoConfigureRestTestClient
class StudentTests {

    @Autowired
    private lateinit var restTestClient: RestTestClient

    @MockkBean
    private lateinit var idGenerator: IdGenerator

    private val baseUrl: String = "http://localhost"
    private val studentUrl: String = "/students"

    @ParameterizedTest
    @ValueSource(strings = ["Jane", "John", "Alice"])
    fun `given i am a student, when i register`(name: String) {

        val expectedId = UUID.randomUUID()
        val studentRequest = RegisterStudentRequest(name)
        every { idGenerator.generate() } returns expectedId

        val registerStudent = registerStudent(studentRequest)

        registerStudent
            .expectStatus().isCreated
            .expectHeader().location("$baseUrl$studentUrl/$expectedId")
            .expectBody<StudentResponse>()
            .value { response ->
                requireNotNull(response)
                response.hasValidId()
                response.nameIsValidAndEqualTo(name)
            }

    }


    @Test
    fun `given a student have been registered student, i can retrieve its information`() {

        val expectedId = UUID.randomUUID()
        val studentRequest = RegisterStudentRequest("John")
        every { idGenerator.generate() } returns expectedId

        registerStudent(studentRequest)
        val studentById = getStudentById(expectedId)
        studentById.expectStatus().isOk


    }

    private fun getStudentById(expectedId: UUID?): RestTestClient.ResponseSpec =
        restTestClient.get().uri("$studentUrl/$expectedId").exchange()

    private fun registerStudent(studentRequest: RegisterStudentRequest): RestTestClient.ResponseSpec =
        restTestClient.post().uri(studentUrl).body(studentRequest).exchange()


}