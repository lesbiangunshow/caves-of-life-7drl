package com.abbisea.caves.messages

import com.abbisea.caves.world.AnyGameEntity
import com.abbisea.caves.world.GameContext
import com.abbisea.caves.world.GameMessage

data class Destroy(
    override val context: GameContext,
    override val source: AnyGameEntity,
    val target: AnyGameEntity,
    val cause: String = "natural causes."
) : GameMessage