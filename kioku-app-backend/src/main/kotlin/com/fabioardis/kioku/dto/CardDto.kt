package com.fabioardis.kioku.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreateCardRequest(
    @field:NotBlank(message = "Front is required")
    @field:Size(min = 1, max = 1000, message = "Front must be between 1 and 1000 characters")
    val front: String,

    @field:NotBlank(message = "Back is required")
    @field:Size(min = 1, max = 1000, message = "Back must be between 1 and 1000 characters")
    val back: String,

    @field:NotNull(message = "Deck ID is required")
    val deckId: Long
)