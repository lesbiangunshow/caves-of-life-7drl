package com.abbisea.caves.systems

import com.abbisea.caves.extensions.AnyGameEntity
import com.abbisea.caves.extensions.position
import com.abbisea.caves.messages.MoveTo
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior

object HunterSeeker : BaseBehavior<GameContext>() {

    override suspend fun update(entity: AnyGameEntity, context: GameContext): Boolean {
        val (world, _, _, player) = context
        val path = world.findPath(entity, player)
        return if (path.isNotEmpty()) {
            entity.receiveMessage(
                MoveTo(
                    context = context,
                    source = entity,
                    position = path.iterator().next().toPosition3D(player.position.z)
                )
            )
            true
        } else false
    }
}