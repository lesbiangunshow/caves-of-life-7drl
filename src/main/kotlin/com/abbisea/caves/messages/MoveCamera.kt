package com.abbisea.caves.messages

import com.abbisea.caves.world.GameContext
import com.abbisea.caves.world.GameEntity
import com.abbisea.caves.world.GameMessage
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Position3D

data class MoveCamera(
    override val context: GameContext,
    override val source: GameEntity<EntityType>,
    val previousPosition: Position3D
) : GameMessage
