package com.abbisea.caves.events

import org.hexworks.cobalt.events.api.Event

data class PlayerWonTheGame(
    val zircons: Int,
    override val emitter: Any
) : Event