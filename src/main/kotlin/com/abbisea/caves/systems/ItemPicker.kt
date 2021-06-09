package com.abbisea.caves.systems

import com.abbisea.caves.attributes.types.Item
import com.abbisea.caves.attributes.types.addItem
import com.abbisea.caves.extensions.GameItem
import com.abbisea.caves.extensions.filterType
import com.abbisea.caves.extensions.isPlayer
import com.abbisea.caves.functions.logGameEvent
import com.abbisea.caves.messages.PickItemUp
import com.abbisea.caves.world.GameContext
import com.abbisea.caves.world.World
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.data.Position3D

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

    private fun World.findTopItem(position: Position3D): Maybe<GameItem> =
        fetchBlockAt(position).flatMap { block ->
            Maybe.ofNullable(block.entities.filterType<Item>().firstOrNull())
        }
}