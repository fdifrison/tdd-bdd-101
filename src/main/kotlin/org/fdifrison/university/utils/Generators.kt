package org.fdifrison.university.utils

import org.springframework.stereotype.Component
import java.util.*

interface IdGenerator {
    fun generate(): UUID
}

@Component
class RandomIdGenerator : IdGenerator {
    override fun generate(): UUID = UUID.randomUUID()
}