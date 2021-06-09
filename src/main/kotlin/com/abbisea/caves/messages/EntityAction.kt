package com.abbisea.caves.messages

import com.abbisea.caves.extensions.GameEntity
import com.abbisea.caves.extensions.GameMessage
import org.hexworks.amethyst.api.entity.EntityType

interface EntityAction<S : EntityType, T : EntityType> : GameMessage {

    val target: GameEntity<T>

    operator fun component1() = context
    operator fun component2() = source
    operator fun component3() = target
}