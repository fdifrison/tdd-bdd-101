package org.fdifrison.university.student

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.fdifrison.university.students.StudentController
import org.fdifrison.university.utils.IdGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.test.web.servlet.client.expectBody
import java.util.*
import kotlin.test.Test

@WebMvcTest(controllers = [StudentController::class])
@AutoConfigureRestTestClient
class StudentTests {

    @Autowired
    private lateinit var restTestClient: RestTestClient

    @MockkBean
    private lateinit var idGenerator: IdGenerator

    private val baseUrl: String = "http://localhost"

    @Test
    fun `given i am a student, when i register`() {

        val expectedId = UUID.randomUUID()
        every { idGenerator.generate() } returns expectedId

        restTestClient
            .post()
            .uri("/students").exchange()
            .expectStatus().isCreated
            .expectHeader().location("$baseUrl/students/$expectedId")
            .expectBody<StudentResponse>()
            .value { response ->
                requireNotNull(response)
                assertThat(response.id)
                    .isNotNull()
                    .isNotEqualTo(UUID(0L, 0L))
            }

    }


}