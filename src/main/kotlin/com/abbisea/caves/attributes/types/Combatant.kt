package com.abbisea.caves.attributes.types

import com.abbisea.caves.attributes.CombatStats
import com.abbisea.caves.world.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

interface Combatant : EntityType

val GameEntity<Combatant>.combatStats: CombatStats
    get() = findAttribute(CombatStats::class).get()