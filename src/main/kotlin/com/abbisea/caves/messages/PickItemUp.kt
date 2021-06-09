package com.abbisea.caves.messages

import com.abbisea.caves.extensions.GameItemHolder
import com.abbisea.caves.extensions.GameMessage
import com.abbisea.caves.world.GameContext
import org.hexworks.zircon.api.data.Position3D

data class PickItemUp(
    override val context: GameContext,
    override val source: GameItemHolder,
    val position: Position3D
) : GameMessage
