package org.fdifrison.university.students

import java.util.*

@ConsistentCopyVisibility
data class Student private constructor(val id: UUID) {
    companion object {
        @JvmStatic // if called from java code remove the .Companion.
        fun register() = Student(UUID.randomUUID())
    }
}
