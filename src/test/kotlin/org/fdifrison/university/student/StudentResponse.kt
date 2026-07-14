package org.fdifrison.university.student

import org.assertj.core.api.Assertions.assertThat
import java.util.*

data class StudentResponse(val id: UUID, val name: String)


fun StudentResponse.hasValidId() {
    assertThat(this.id)
        .isNotNull()
        .isNotEqualTo(UUID(0L, 0L))
}

fun StudentResponse.nameIsValidAndEqualTo(name: String) {
    assertThat(this.name)
        .isNotBlank()
        .isEqualTo(name)
}
