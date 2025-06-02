package com.fabioardis.kioku.controller

import com.fabioardis.kioku.dto.CardResponse
import com.fabioardis.kioku.dto.CreateDeckRequest
import com.fabioardis.kioku.dto.DeckResponse
import com.fabioardis.kioku.dto.UpdateDeckRequest
import com.fabioardis.kioku.exception.ResourceNotFoundException
import com.fabioardis.kioku.model.Card
import com.fabioardis.kioku.model.Deck
import com.fabioardis.kioku.repository.CardRepository
import com.fabioardis.kioku.service.DeckService

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

@RestController
@RequestMapping("/api/decks")
@Tag(name = "Decks", description = "API that manages decks")
class DeckController (
    private val deckService: DeckService,
    private val cardRepository: CardRepository
) {

    @GetMapping
    @Operation(summary = "Get all the decks", description = "Returns a list of all the available decks")
    @ApiResponse(responseCode = "200", description = "Decks list successfully returned")
    fun getAllDecks(): List<DeckResponse> {
        val decks: List<Deck> = deckService.getAllDecks()
        return decks.map { deckService.toDto(it) }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a deck by ID", description = "Returns a single deck given his ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Deck found"),
        ApiResponse(responseCode = "404", description = "Deck not found")
    ])
    fun getDeckById(
        @Parameter(description = "ID of the deck to retrieve")
        @PathVariable id: Long
    ): ResponseEntity<DeckResponse> {
        val deck = deckService.getDeckById(id)
            ?: throw ResourceNotFoundException("Deck with id $id not found")

        val dto = deckService.toDto(deck)
        return ResponseEntity.ok(dto)
    }

    @PostMapping
    @Operation(summary = "Create a new deck", description = "Creates a new deck")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Deck created successfully"),
        ApiResponse(responseCode = "400", description = "Data is not valid")
    ])
    fun createDeck(
        @Parameter(description = "New deck's data")
        @Valid @RequestBody request: CreateDeckRequest
    ): ResponseEntity<Deck> {
        val deck = Deck(
            name = request.name,
            description = request.description
        )
        val createdDeck = deckService.createDeck(deck)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDeck)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a deck", description = "Updates the data of an existing deck")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Deck updated successfully"),
        ApiResponse(responseCode = "404", description = "Deck not found"),
        ApiResponse(responseCode = "400", description = "Data is not valid")
    ])
    fun updateDeck(
        @Parameter(description = "ID of the deck to be updated")
        @PathVariable id: Long,
        @Parameter(description = "Updated deck's data")
        @Valid @RequestBody request: UpdateDeckRequest
    ): Deck {
        val existingDeck = deckService.getDeckById(id)
            ?: throw ResourceNotFoundException("Deck with id $id not found")

        val updatedDeck = existingDeck.copy(
            name = request.name ?: existingDeck.name,
            description = request.description ?: existingDeck.description
        )

        return deckService.createDeck(updatedDeck)
    }

    @GetMapping("/{id}/cards")
    @Operation(summary = "Get all the cards from a deck", description = "Returns all the cards from a specific deck, given the ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Cards list returned successfully"),
        ApiResponse(responseCode = "404", description = "Deck not found")
    ])
    fun getCardsByDeckId(
        @Parameter(description = "ID of the deck where we're getting the cards from")
        @PathVariable id: Long
    ): List<CardResponse> {
        deckService.getDeckById(id)
            ?: throw ResourceNotFoundException("Deck with $id not found")

        val cards: List<Card> = cardRepository.findByDeckId(id)
        return cards.map { deckService.toCardDto(it) }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a deck", description = "Deletes a deck and all its cards given its ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Deck deleted successfully"),
        ApiResponse(responseCode = "404", description = "Deck not found")
    ])
    fun deleteDeck(@PathVariable id: Long): ResponseEntity<Void> {
        if (deckService.getDeckById(id) == null) {
            throw ResourceNotFoundException("Deck with id $id not found")
        }

        deckService.deleteDeck(id)
        return ResponseEntity.noContent().build()
    }
}