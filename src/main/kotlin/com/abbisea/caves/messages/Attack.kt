package com.abbisea.caves.messages

import com.abbisea.caves.attributes.types.Combatant
import com.abbisea.caves.extensions.GameEntity
import com.abbisea.caves.world.GameContext

data class Attack(
    override val context: GameContext,
    override val source: GameEntity<Combatant>,
    override val target: GameEntity<Combatant>
) : EntityAction<Combatant, Combatant>