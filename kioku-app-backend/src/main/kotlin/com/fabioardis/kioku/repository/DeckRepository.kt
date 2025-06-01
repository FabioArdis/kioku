package com.fabioardis.kioku.repository

import com.fabioardis.kioku.model.Deck
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeckRepository : JpaRepository<Deck, Long>