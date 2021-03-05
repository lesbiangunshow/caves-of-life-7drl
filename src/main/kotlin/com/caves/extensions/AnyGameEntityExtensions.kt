package com.caves.extensions

import com.caves.attributes.EntityPosition
import com.caves.attributes.EntityTile
import com.caves.world.AnyGameEntity
import org.hexworks.amethyst.api.Attribute
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Tile
import kotlin.reflect.KClass

var AnyGameEntity.position: Position3D
    get() = tryToFindAttribute(EntityPosition::class).position
    set(value) {
        findAttribute(EntityPosition::class).map {
            it.position = value
        }
    }

val AnyGameEntity.tile: Tile
    get() = tryToFindAttribute(EntityTile::class).tile

fun <T: Attribute> AnyGameEntity.tryToFindAttribute(klass: KClass<T>): T =
    findAttribute(klass).orElseThrow { NoSuchElementException("Entity '$this' has no property with type '${klass.simpleName}'.") }