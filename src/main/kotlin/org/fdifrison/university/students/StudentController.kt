package org.fdifrison.university.students

import org.fdifrison.university.utils.IdGenerator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StudentController(
    private val idGenerator: IdGenerator
) {
    @PostMapping("/students")
    fun registerStudent(): ResponseEntity<Student> {
        val student = Student.register(idGenerator.generate())
        return ResponseEntity(student, HttpStatus.CREATED)
    }
}