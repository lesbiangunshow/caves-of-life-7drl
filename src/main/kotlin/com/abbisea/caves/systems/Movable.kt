package com.abbisea.caves.systems

import com.abbisea.caves.attributes.types.Player
import com.abbisea.caves.extensions.position
import com.abbisea.caves.extensions.tryActionsOn
import com.abbisea.caves.messages.MoveCamera
import com.abbisea.caves.messages.MoveTo
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.MessageResponse
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object Movable: BaseFacet<GameContext, MoveTo>(MoveTo::class) {

    override suspend fun receive(message: MoveTo): Response {
        val (context, entity, position) = message
        val world = context.world
        val previousPosition = entity.position
        var result: Response = Pass
        world.fetchBlockAtOrNull(position)?.let { block ->
            if (block.isOccupied) {
                result = entity.tryActionsOn(context, block.occupier.get())
            } else {

                if (world.moveEntity(entity, position)) {
                    result = if (entity.type == Player) {
                        MessageResponse(
                            MoveCamera(context, entity, previousPosition)
                        )
                    } else {
                        Consumed
                    }
                }
            }
        }
        return result
    }
}