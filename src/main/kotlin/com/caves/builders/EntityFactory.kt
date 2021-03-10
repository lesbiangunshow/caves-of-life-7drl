package com.caves.builders

import com.caves.attributes.EntityPosition
import com.caves.attributes.EntityTile
import com.caves.attributes.types.Player
import com.caves.world.GameContext
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType


fun <T: EntityType> newGameEntityOfType(
    type: T,
    init: EntityBuilder<T, GameContext>.() -> Unit
) = newEntityOfType(type, init)

object EntityFactory {

    fun newPlayer() = newGameEntityOfType(Player) {
        attributes(EntityPosition(), EntityTile(PLAYER))
        behaviors()
        facets()
    }
}