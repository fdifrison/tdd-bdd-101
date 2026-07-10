package org.fdifrison.university.student

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.web.servlet.client.RestTestClient
import kotlin.test.Test

@WebMvcTest
@AutoConfigureRestTestClient
class StudentTests {

    @Autowired
    private lateinit var restTestClient: RestTestClient

    @Test
    fun `given i am a student, when i register`() {
        restTestClient
            .post()
            .uri("/students")
            .exchange()
            .expectStatus()
            .isCreated
    }


}