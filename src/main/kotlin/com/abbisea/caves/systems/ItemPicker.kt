package com.abbisea.caves.systems

import com.abbisea.caves.attributes.types.addItem
import com.abbisea.caves.extensions.isPlayer
import com.abbisea.caves.functions.logGameEvent
import com.abbisea.caves.messages.PickItemUp
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object ItemPicker : BaseFacet<GameContext, PickItemUp>(PickItemUp::class) {

    override suspend fun receive(message: PickItemUp): Response {
        val (context, itemHolder, position) = message
        val world = context.world
        world.findTopItem(position).map { item ->
            if (itemHolder.addItem(item)) {
                world.removeEntity(item)
                val subject = if (itemHolder.isPlayer) "You" else "The $itemHolder"
                val verb = if (itemHolder.isPlayer) "pick up" else "picks up"
                logGameEvent("$subject $verb the $item.", this)
            }
        }
        return Consumed
    }
}