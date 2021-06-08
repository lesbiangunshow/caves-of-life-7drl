package com.abbisea.caves.messages

import com.abbisea.caves.world.AnyGameEntity
import com.abbisea.caves.world.GameContext
import com.abbisea.caves.world.GameMessage

data class MoveUp(
    override val context: GameContext,
    override val source: AnyGameEntity
): GameMessage