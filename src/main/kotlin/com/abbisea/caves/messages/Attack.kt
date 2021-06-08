package com.abbisea.caves.messages

import com.abbisea.caves.attributes.types.Combatant
import com.abbisea.caves.world.AnyGameEntity
import com.abbisea.caves.world.GameContext
import com.abbisea.caves.world.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

data class Attack(
    override val context: GameContext,
    override val source: GameEntity<Combatant>,
    override val target: GameEntity<Combatant>
) : EntityAction<Combatant, Combatant>