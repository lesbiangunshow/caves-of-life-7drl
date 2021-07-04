package com.abbisea.caves.attributes.types

import com.abbisea.caves.attributes.CombatStats
import com.abbisea.caves.attributes.Experience
import com.abbisea.caves.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

interface ExperienceGainer : EntityType

val GameEntity<ExperienceGainer>.experience: Experience
    get() = findAttribute(Experience::class).get()

val GameEntity<ExperienceGainer>.combatStats: CombatStats
    get() = findAttribute(CombatStats::class).get()
