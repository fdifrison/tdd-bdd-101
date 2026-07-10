package org.fdifrison.university.students

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StudentController {

    @PostMapping("/students")
    fun registerStudent(): ResponseEntity<String> {
        return ResponseEntity("", HttpStatus.CREATED)
    }
}