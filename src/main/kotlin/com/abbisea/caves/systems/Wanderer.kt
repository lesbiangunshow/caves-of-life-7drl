package com.abbisea.caves.systems

import com.abbisea.caves.extensions.position
import com.abbisea.caves.extensions.sameLevelNeighboursShuffled
import com.abbisea.caves.messages.MoveTo
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

object Wanderer : BaseBehavior<GameContext>() {

    override suspend fun update(entity: Entity<EntityType, GameContext>, context: GameContext): Boolean {
        val pos = entity.position
        if (pos.isUnknown.not()) {
            entity.receiveMessage(
                MoveTo(
                    context = context,
                    source = entity,
                    position = pos.sameLevelNeighboursShuffled().first()
                )
            )
            return true
        }
        return false
    }
}