package org.fdifrison.university.students

import jakarta.validation.Valid
import org.fdifrison.university.utils.IdGenerator
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

@RestController
class StudentController(
    private val idGenerator: IdGenerator,
    private val studentService: IStudentService
) {
    @PostMapping("students")
    fun registerStudent(@RequestBody @Valid request: RegistrationStudentRequest): ResponseEntity<Student> {
        val student = Student.register(idGenerator.generate(), request.name)
        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(student.id)
            .toUri()
        return ResponseEntity.created(location).body(student)
    }

    @GetMapping("students/{id}")
    fun getStudent(@PathVariable id: UUID) : ResponseEntity<Student> {
        val student = studentService.getStudent(id)
        return ResponseEntity.ok(student)
    }
}