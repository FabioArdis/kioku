package com.fabioardis.kioku.controller

import com.fabioardis.kioku.model.Card
import com.fabioardis.kioku.model.Deck
import com.fabioardis.kioku.repository.CardRepository
import com.fabioardis.kioku.service.DeckService

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/decks")
class DeckController (
    private val deckService: DeckService,
    private val cardRepository: CardRepository
) {

    @GetMapping
    fun getAllDecks(): List<Deck> = deckService.getAllDecks()

    @GetMapping("/{id}")
    fun getDeckById(@PathVariable id: Long): ResponseEntity<Deck> {
        val deck = deckService.getDeckById(id)
        return if (deck != null) {
            ResponseEntity.ok(deck)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createDeck(@RequestBody deck: Deck): ResponseEntity<Deck> {
        val createdDeck = deckService.createDeck(deck)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDeck)
    }

    @GetMapping("/{id}/cards")
    fun getCardsByDeckId(@PathVariable id: Long): List<Card> {
        return cardRepository.findByDeckId(id)
    }

    @DeleteMapping("/{id}")
    fun deleteDeck(@PathVariable id: Long): ResponseEntity<Void> {
        deckService.deleteDeck(id)
        return ResponseEntity.noContent().build()
    }
}