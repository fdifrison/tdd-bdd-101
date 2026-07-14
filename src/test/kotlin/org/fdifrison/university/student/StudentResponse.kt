package org.fdifrison.university.student

import org.assertj.core.api.Assertions.assertThat
import java.util.*

data class StudentResponse(val id: UUID, val name: String)


fun StudentResponse.hasValidIdAndEqualTo(expectedId: UUID) {
    assertThat(this.id)
        .isNotNull()
        .isEqualTo(expectedId)
}

fun StudentResponse.nameIsValidAndEqualTo(name: String) {
    assertThat(this.name)
        .isNotBlank()
        .isEqualTo(name)
}
