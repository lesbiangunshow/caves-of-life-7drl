package com.abbisea.caves.systems

import com.abbisea.caves.attributes.types.removeItem
import com.abbisea.caves.extensions.isPlayer
import com.abbisea.caves.functions.logGameEvent
import com.abbisea.caves.messages.DropItem
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object ItemDropper : BaseFacet<GameContext, DropItem>(DropItem::class) {

    override suspend fun receive(message: DropItem): Response {
        val (context, itemHolder, item, position) = message
        if (itemHolder.removeItem(item)) {
            context.world.addEntity(item, position)
            val subject = if (itemHolder.isPlayer) "You" else "The $itemHolder"
            val verb = if (itemHolder.isPlayer) "drop" else "drops"
            logGameEvent("$subject $verb the $item.", this)
        }
        return Consumed
    }
}