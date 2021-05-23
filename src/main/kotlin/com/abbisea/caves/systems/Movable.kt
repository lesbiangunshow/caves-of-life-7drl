package com.abbisea.caves.systems

import com.abbisea.caves.messages.MoveTo
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object Movable: BaseFacet<GameContext, MoveTo>(MoveTo::class) {

    override suspend fun receive(message: MoveTo): Response {
        val (context, entity, position) = message
        val world = context.world
        var result: Response = Pass
        if (world.moveEntity(entity, position)) {
            result = Consumed
        }

        return result
    }
}