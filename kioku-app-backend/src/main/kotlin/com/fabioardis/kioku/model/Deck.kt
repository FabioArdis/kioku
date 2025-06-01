package com.fabioardis.kioku.model

import jakarta.persistence.*

@Entity
@Table(name = "decks")
data class Deck(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    val description: String? = null,

    @OneToMany(mappedBy = "deck", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val cards: List<Card> = emptyList()
)