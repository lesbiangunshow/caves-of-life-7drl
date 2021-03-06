package com.abbisea.caves.systems

import com.abbisea.caves.functions.logGameEvent
import com.abbisea.caves.messages.Destroy
import com.abbisea.caves.messages.EntityDestroyed
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object Destructible : BaseFacet<GameContext, Destroy>(Destroy::class) {
    override suspend fun receive(message: Destroy): Response {
        val (context, destroyer, target, cause) = message
        context.world.removeEntity(target)
        destroyer.receiveMessage(EntityDestroyed(context, target, destroyer))
        logGameEvent("$target dies after receiving $cause.", Destructible)
        return Consumed
    }
}