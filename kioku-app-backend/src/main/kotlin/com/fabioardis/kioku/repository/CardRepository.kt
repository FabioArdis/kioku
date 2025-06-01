package com.fabioardis.kioku.repository

import com.fabioardis.kioku.model.Card
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CardRepository : JpaRepository<Card, Long> {

    @Query("SELECT c FROM Card c WHERE c.deck.id = :deckId")
    fun findByDeckId(deckId: Long): List<Card>
}