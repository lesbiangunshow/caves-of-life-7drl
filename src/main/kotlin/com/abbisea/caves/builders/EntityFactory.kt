package com.abbisea.caves.builders

import com.abbisea.caves.attributes.EntityActions
import com.abbisea.caves.attributes.EntityPosition
import com.abbisea.caves.attributes.EntityTile
import com.abbisea.caves.attributes.flags.BlockOccupier
import com.abbisea.caves.attributes.types.Player
import com.abbisea.caves.attributes.types.Wall
import com.abbisea.caves.builders.GameTileRepository.PLAYER
import com.abbisea.caves.messages.Dig
import com.abbisea.caves.systems.CameraMover
import com.abbisea.caves.systems.Diggable
import com.abbisea.caves.systems.InputReceiver
import com.abbisea.caves.systems.Movable
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType

fun <T : EntityType> newGameEntityOfType(
    type: T,
    init: EntityBuilder<T, GameContext>.() -> Unit
) = newEntityOfType(type, init)

object EntityFactory {

    fun newPlayer() = newGameEntityOfType(Player) {
        attributes(
            EntityPosition(),
            EntityTile(PLAYER),
            EntityActions(Dig::class)
        )
        behaviors(InputReceiver)
        facets(Movable, CameraMover)
    }

    fun newWall() = newGameEntityOfType(Wall) {
        attributes(EntityPosition(), BlockOccupier, EntityTile(GameTileRepository.WALL))
        facets(Diggable)
    }
}