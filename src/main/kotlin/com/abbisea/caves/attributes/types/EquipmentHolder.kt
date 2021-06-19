package com.abbisea.caves.attributes.types

import com.abbisea.caves.attributes.Equipment
import com.abbisea.caves.attributes.Inventory
import com.abbisea.caves.extensions.GameCombatItem
import com.abbisea.caves.extensions.GameEquipmentHolder
import org.hexworks.amethyst.api.entity.EntityType

interface EquipmentHolder : EntityType

fun GameEquipmentHolder.equip(inventory: Inventory, item: GameCombatItem) =
    equipment.equip(inventory, item)

val GameEquipmentHolder.equipment: Equipment
    get() = findAttribute(Equipment::class).get()