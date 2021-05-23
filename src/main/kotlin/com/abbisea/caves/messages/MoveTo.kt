package com.abbisea.caves.messages

import com.abbisea.caves.world.GameContext
import com.abbisea.caves.world.GameMessage
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Position3D

data class MoveTo(
    override val context: GameContext,
    override val source: Entity<EntityType, GameContext>,
    val position: Position3D
): GameMessage
