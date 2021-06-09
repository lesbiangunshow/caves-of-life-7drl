package com.abbisea.caves.attributes.types

import com.abbisea.caves.attributes.Inventory
import com.abbisea.caves.extensions.GameItem
import com.abbisea.caves.extensions.GameItemHolder
import org.hexworks.amethyst.api.entity.EntityType

interface ItemHolder : EntityType

fun GameItemHolder.addItem(item: GameItem) = inventory.addItem(item)

fun GameItemHolder.removeItem(item: GameItem) = inventory.removeItem(item)

val GameItemHolder.inventory: Inventory
    get() = findAttribute(Inventory::class).get()