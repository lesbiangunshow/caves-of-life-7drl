package com.abbisea.caves.attributes.types

import com.abbisea.caves.extensions.GameEntity

interface Armour : CombatItem

val GameEntity<Armour>.attackValue: Int
    get() = findAttribute(com.abbisea.caves.attributes.ItemCombatStats::class).get().attackValue

val GameEntity<Armour>.defenseValue: Int
    get() = findAttribute(com.abbisea.caves.attributes.ItemCombatStats::class).get().defenseValue
