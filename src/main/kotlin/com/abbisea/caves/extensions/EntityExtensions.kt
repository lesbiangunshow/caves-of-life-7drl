package com.abbisea.caves.extensions

import com.abbisea.caves.attributes.EntityActions
import com.abbisea.caves.attributes.EntityPosition
import com.abbisea.caves.attributes.EntityTile
import com.abbisea.caves.attributes.flags.BlockOccupier
import com.abbisea.caves.world.AnyGameEntity
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
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

val AnyGameEntity.occupiesBlock: Boolean
    get() = findAttribute(BlockOccupier::class).isPresent

suspend fun AnyGameEntity.tryActionsOn(context: GameContext, target: AnyGameEntity): Response {
    var result: Response = Pass
    findAttributeOrNull(EntityActions::class)?.let {
        for (action in it.createActionsFor(context, this, target)) {
            if (target.receiveMessage(action) is Consumed) {
                result = Consumed
                return@let
            }
        }
    }
    return result
}