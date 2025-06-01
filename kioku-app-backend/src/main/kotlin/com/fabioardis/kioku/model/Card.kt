package com.fabioardis.kioku.model

import jakarta.persistence.*

@Entity
@Table(name = "cards")
data class Card (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val front: String,

    @Column(nullable = false)
    val back: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id")
    val deck: Deck
)