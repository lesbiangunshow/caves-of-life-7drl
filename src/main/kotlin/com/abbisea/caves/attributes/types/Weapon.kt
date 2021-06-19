package com.abbisea.caves.attributes.types

import com.abbisea.caves.attributes.ItemCombatStats
import com.abbisea.caves.extensions.GameEntity

interface Weapon : CombatItem

val GameEntity<Weapon>.attackValue: Int
    get() = findAttribute(ItemCombatStats::class).get().attackValue

val GameEntity<Weapon>.defenseValue: Int
    get() = findAttribute(ItemCombatStats::class).get().defenseValue
