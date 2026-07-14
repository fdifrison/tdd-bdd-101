package org.fdifrison.university.students

import java.util.*

@ConsistentCopyVisibility
data class Student private constructor(val id: UUID, val name: String) {
    companion object {
        @JvmStatic // if called from java code remove the .Companion.
        fun register(id: UUID, name: String) = Student(id, name)
    }
}
