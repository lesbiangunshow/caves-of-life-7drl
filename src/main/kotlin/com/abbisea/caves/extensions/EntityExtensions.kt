package com.abbisea.caves.extensions

import com.abbisea.caves.attributes.EntityActions
import com.abbisea.caves.attributes.EntityPosition
import com.abbisea.caves.attributes.EntityTile
import com.abbisea.caves.attributes.flags.BlockOccupier
import com.abbisea.caves.attributes.flags.VisionBlocker
import com.abbisea.caves.attributes.types.Combatant
import com.abbisea.caves.attributes.types.Player
import com.abbisea.caves.attributes.types.combatStats
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Tile
import kotlin.reflect.KClass
import kotlin.reflect.full.isSuperclassOf

var AnyGameEntity.position: Position3D
    get() = tryToFindAttribute(EntityPosition::class).position
    set(value) {
        findAttribute(EntityPosition::class).map {
            it.position = value
        }
    }

val AnyGameEntity.tile: Tile
    get() = tryToFindAttribute(EntityTile::class).tile

fun <T : Attribute> AnyGameEntity.tryToFindAttribute(klass: KClass<T>): T =
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

val AnyGameEntity.blocksVision: Boolean
    get() = this.findAttribute(VisionBlocker::class).isPresent

val AnyGameEntity.isPlayer: Boolean
    get() = this.type == Player

fun GameEntity<Combatant>.hasNoHealthLeft(): Boolean = combatStats.hp <= 0

@Suppress("UNCHECKED_CAST")
inline fun <reified T : EntityType> Iterable<AnyGameEntity>.filterType(): List<Entity<T, GameContext>> =
    filter { T::class.isSuperclassOf(it.type::class) }.toList() as List<Entity<T, GameContext>>
