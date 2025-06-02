package com.fabioardis.kioku.service

import com.fabioardis.kioku.config.logger
import com.fabioardis.kioku.dto.CardResponse
import com.fabioardis.kioku.dto.DeckResponse
import com.fabioardis.kioku.model.Card
import com.fabioardis.kioku.model.Deck
import com.fabioardis.kioku.repository.DeckRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeckService(private val deckRepository: DeckRepository) {

    private val log = logger()

    @Transactional(readOnly = true)
    fun getAllDecks(): List<Deck> {
        log.debug("Fetching all decks")
        return deckRepository.findAll().also {
            log.info("Retrieved ${it.size} decks")
        }
    }

    @Transactional(readOnly = true)
    fun getDeckById(id: Long): Deck? {
        log.debug("Fetching deck with id $id")
        return deckRepository.findById(id).orElse(null).also { deck ->
            if (deck != null) {
                log.debug("Found deck: ${deck.name}")
            } else {
                log.debug("Deck with id $id not found")
            }
        }
    }

    fun createDeck(deck: Deck): Deck {
        log.info("Creating new deck: ${deck.name}")
        return deckRepository.save(deck).also {
            log.info("Successfully created deck with id: ${it.id}")
        }
    }

    fun deleteDeck(id: Long) {
        log.info("Deleting deck with id: $id")
        deckRepository.deleteById(id)
        log.info("Successfully deleted deck with id: $id")
    }

    fun toDto(deck: Deck): DeckResponse {
        val cardResponse = deck.cards.map { card ->
            CardResponse(
                id = card.id,
                front = card.front,
                back = card.back,
                createdAt = card.createdAt,
                updatedAt = card.updatedAt
            )
        }
        return DeckResponse(
            id          = deck.id,
            name        = deck.name,
            description = deck.description,
            createdAt   = deck.createdAt,
            updatedAt   = deck.updatedAt,
            cards       = cardResponse
        )
    }

    fun toCardDto(card: Card): CardResponse {
        return CardResponse(
            id        = card.id,
            front     = card.front,
            back      = card.back,
            createdAt = card.createdAt,
            updatedAt = card.updatedAt
        )
    }
}