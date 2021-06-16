package com.abbisea.caves.systems

import com.abbisea.caves.attributes.types.energy
import com.abbisea.caves.attributes.types.energyLevel
import com.abbisea.caves.extensions.isPlayer
import com.abbisea.caves.functions.logGameEvent
import com.abbisea.caves.messages.Eat
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object DigestiveSystem : BaseFacet<GameContext, Eat>(Eat::class) {

    override suspend fun receive(message: Eat): Response {
        val (_, entity, food) = message
        entity.energyLevel.currentEnergy += food.energy
        val verb = if (entity.isPlayer) "You eat" else "The $entity eats"
        logGameEvent("$verb the $food.", this)
        return Consumed
    }
}