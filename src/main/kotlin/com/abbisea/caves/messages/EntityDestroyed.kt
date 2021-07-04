package com.abbisea.caves.messages

import com.abbisea.caves.extensions.AnyGameEntity
import com.abbisea.caves.extensions.GameMessage
import com.abbisea.caves.world.GameContext

data class EntityDestroyed(
    override val context: GameContext,
    override val source: AnyGameEntity,
    val destroyer: AnyGameEntity
) : GameMessage
