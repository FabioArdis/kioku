package com.fabioardis.kioku.controller

import com.fabioardis.kioku.dto.CreateCardRequest
import com.fabioardis.kioku.exception.ResourceNotFoundException
import com.fabioardis.kioku.model.Card
import com.fabioardis.kioku.repository.CardRepository
import com.fabioardis.kioku.service.DeckService

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cards")
class CardController(
    private val cardRepository: CardRepository,
    private val deckService: DeckService
) {
    @GetMapping
    fun getAllCards(): List<Card> = cardRepository.findAll()

    @GetMapping("/{id}")
    fun getCardById(@PathVariable id: Long): Card {
        return cardRepository.findById(id).orElse(null)
            ?: throw ResourceNotFoundException("Card with id $id not found")
    }

    @PostMapping
    fun createCard(@Valid @RequestBody request: CreateCardRequest): ResponseEntity<Card> {
        val deck = deckService.getDeckById(request.deckId)
            ?: throw ResourceNotFoundException("Deck with id ${request.deckId} not found")

        val card = Card(
            front = request.front,
            back = request.back,
            deck = deck
        )

        val savedCard = cardRepository.save(card)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCard)
    }

    @DeleteMapping("/{id}")
    fun deleteCard(@PathVariable id: Long): ResponseEntity<Void> {
        if (!cardRepository.existsById(id)) {
            throw ResourceNotFoundException("Card with id $id not found")
        }

        cardRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}