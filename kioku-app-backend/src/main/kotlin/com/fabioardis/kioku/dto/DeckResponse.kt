package com.fabioardis.kioku.dto

import java.time.LocalDateTime

data class DeckResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val cards: List<CardResponse>
)