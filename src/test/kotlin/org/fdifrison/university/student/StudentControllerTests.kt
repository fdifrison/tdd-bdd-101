package org.fdifrison.university.student

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.fdifrison.university.students.Student
import org.fdifrison.university.students.StudentController
import org.fdifrison.university.students.IStudentService
import org.fdifrison.university.utils.IdGenerator
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.test.web.servlet.client.returnResult
import java.util.*

@WebMvcTest(controllers = [StudentController::class])
@AutoConfigureRestTestClient
class StudentControllerTests {

    @Autowired
    private lateinit var restTestClient: RestTestClient

    @MockkBean
    private lateinit var idGenerator: IdGenerator

    @MockkBean
    private lateinit var studentService: IStudentService

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
            .returnResult<StudentResponse>().responseBody.run {
                requireNotNull(this)
                this.hasValidIdAndEqualTo(expectedId)
                this.nameIsValidAndEqualTo(name)
            }

    }


    @Test
    fun `given a student have been registered, i can retrieve its information`() {

        val expectedId = UUID.randomUUID()
        val expectedStudent = Student.register(expectedId, "John")
        every { studentService.getStudent(any()) } returns expectedStudent

        val studentById = getStudentById(expectedId)
        studentById.expectStatus().isOk
            .returnResult<StudentResponse>().responseBody.run {
                requireNotNull(this)
                assertThat(this.id).isEqualTo(expectedStudent.id)
                assertThat(this.name).isEqualTo(expectedStudent.name)
            }

    }

    private fun getStudentById(expectedId: UUID?): RestTestClient.ResponseSpec =
        restTestClient.get().uri("$studentUrl/{id}", expectedId).exchange()

    private fun registerStudent(studentRequest: RegisterStudentRequest): RestTestClient.ResponseSpec =
        restTestClient.post().uri(studentUrl).body(studentRequest).exchange()


}