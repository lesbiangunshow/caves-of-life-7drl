package com.abbisea.caves.systems

import com.abbisea.caves.messages.Dig
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object Diggable: BaseFacet<GameContext, Dig>(Dig::class) {
    override suspend fun receive(message: Dig): Response {
        val (context, _, target) = message
        context.world.removeEntity(target)
        return Consumed
    }
}