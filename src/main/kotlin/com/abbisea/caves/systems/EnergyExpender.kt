package com.abbisea.caves.systems

import com.abbisea.caves.attributes.EnergyLevel
import com.abbisea.caves.attributes.types.EnergyUser
import com.abbisea.caves.attributes.types.energyLevel
import com.abbisea.caves.extensions.AnyGameEntity
import com.abbisea.caves.extensions.whenTypeIs
import com.abbisea.caves.messages.Destroy
import com.abbisea.caves.messages.Expend
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseActor
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

object EnergyExpender : BaseActor<GameContext, Expend>(Expend::class, EnergyLevel::class) {

    override suspend fun receive(message: Expend): Response {
        val (context, entity, energy) = message
        entity.energyLevel.currentEnergy -= energy
        checkStarvation(context, entity, entity.energyLevel)
        return Consumed
    }

    override suspend fun update(entity: Entity<EntityType, GameContext>, context: GameContext): Boolean {
        entity.whenTypeIs<EnergyUser> {
            entity.receiveMessage(
                Expend(
                    context = context,
                    source = it,
                    energy = 2
                )
            )
        }
        return true
    }

    private suspend fun checkStarvation(
        context: GameContext,
        entity: AnyGameEntity,
        energyLevel: EnergyLevel
    ) {
        if (energyLevel.currentEnergy <= 0) {
            entity.receiveMessage(
                Destroy(
                    context = context,
                    source = entity,
                    target = entity,
                    cause = "starvation"
                )
            )
        }
    }
}
