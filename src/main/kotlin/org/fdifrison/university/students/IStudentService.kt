package org.fdifrison.university.students

import org.springframework.stereotype.Service
import java.util.*

interface IStudentService {
    fun getStudent(id: UUID): Student
}

@Service
class StudentService : IStudentService {
    override fun getStudent(id: UUID): Student {
        TODO()
    }
}
