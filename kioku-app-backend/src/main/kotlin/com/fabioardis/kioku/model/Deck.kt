package com.fabioardis.kioku.model

import jakarta.persistence.*
import java.time.LocalDateTime
import com.fasterxml.jackson.annotation.JsonManagedReference

@Entity
@Table(name = "decks")
data class Deck(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    val description: String? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "deck", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonManagedReference
    val cards: List<Card> = emptyList()
)