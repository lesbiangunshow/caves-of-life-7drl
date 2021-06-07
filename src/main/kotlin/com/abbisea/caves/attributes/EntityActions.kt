package com.abbisea.caves.attributes

import com.abbisea.caves.messages.EntityAction
import com.abbisea.caves.world.AnyGameEntity
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.amethyst.api.entity.EntityType
import java.lang.Exception
import kotlin.reflect.KClass

class EntityActions(
    private vararg val actions: KClass<out EntityAction<out EntityType, out EntityType>>
): BaseAttribute() {

    fun createActionsFor(
        context: GameContext,
        source: AnyGameEntity,
        target: AnyGameEntity
    ) : Iterable<EntityAction<out EntityType, out EntityType>> =
        actions.map {
            try {
                it.constructors.first().call(context, source, target)
            } catch (e: Exception) {
                throw IllegalArgumentException("Can't create EntityAction. Does it have the proper constructor?")
            }
        }

}