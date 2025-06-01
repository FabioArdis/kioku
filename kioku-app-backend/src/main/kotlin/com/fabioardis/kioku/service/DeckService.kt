package com.fabioardis.kioku.service

import com.fabioardis.kioku.model.Deck
import com.fabioardis.kioku.repository.DeckRepository
import org.springframework.stereotype.Service

@Service
class DeckService(private val deckRepository: DeckRepository) {

    fun getAllDecks(): List<Deck> = deckRepository.findAll()

    fun getDeckById(id: Long): Deck? = deckRepository.findById(id).orElse(null)

    fun createDeck(deck: Deck): Deck = deckRepository.save(deck)

    fun deleteDeck(id: Long) = deckRepository.deleteById(id)
}