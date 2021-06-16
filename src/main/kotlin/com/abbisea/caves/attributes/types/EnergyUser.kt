package com.abbisea.caves.attributes.types

import com.abbisea.caves.attributes.EnergyLevel
import com.abbisea.caves.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

interface EnergyUser : EntityType

val GameEntity<EnergyUser>.energyLevel: EnergyLevel
    get() = findAttribute(EnergyLevel::class).get()