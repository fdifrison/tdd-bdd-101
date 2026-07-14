package org.fdifrison.university.students

import jakarta.validation.constraints.NotBlank


data class RegistrationStudentRequest(@NotBlank val name: String)
