package org.fdifrison.university.students

import org.fdifrison.university.utils.IdGenerator
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
class StudentController(
    private val idGenerator: IdGenerator
) {
    @PostMapping("/students")
    fun registerStudent(): ResponseEntity<Student> {
        val student = Student.register(idGenerator.generate())
        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(student.id)
            .toUri()
        return ResponseEntity.created(location).body(student)
    }
}