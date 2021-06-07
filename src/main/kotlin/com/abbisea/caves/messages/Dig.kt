package com.abbisea.caves.messages

import com.abbisea.caves.world.AnyGameEntity
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType

data class Dig(
    override val context: GameContext,
    override val source: AnyGameEntity,
    override val target: AnyGameEntity
): EntityAction<EntityType, EntityType>