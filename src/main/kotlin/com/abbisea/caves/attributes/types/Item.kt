package com.abbisea.caves.attributes.types

import com.abbisea.caves.attributes.EntityTile
import com.abbisea.caves.attributes.ItemIcon
import com.abbisea.caves.extensions.GameItem
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.GraphicalTile
import org.hexworks.zircon.api.data.Tile

interface Item : EntityType

val GameItem.tile: Tile
    get() = findAttribute(EntityTile::class).get().tile

val GameItem.iconTile: GraphicalTile
    get() = findAttribute(ItemIcon::class).get().iconTile