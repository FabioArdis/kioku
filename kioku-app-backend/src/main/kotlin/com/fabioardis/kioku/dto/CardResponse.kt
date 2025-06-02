package com.fabioardis.kioku.dto

import java.time.LocalDateTime

data class CardResponse(
    val id: Long,
    val front: String,
    val back: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)