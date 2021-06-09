package com.abbisea.caves.messages

import com.abbisea.caves.extensions.GameEntity
import com.abbisea.caves.extensions.GameMessage
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Position3D

data class MoveCamera(
    override val context: GameContext,
    override val source: GameEntity<EntityType>,
    val previousPosition: Position3D
) : GameMessage
