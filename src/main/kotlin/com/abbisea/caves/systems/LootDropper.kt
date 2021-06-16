package com.abbisea.caves.systems

import com.abbisea.caves.attributes.types.ItemHolder
import com.abbisea.caves.attributes.types.inventory
import com.abbisea.caves.extensions.position
import com.abbisea.caves.extensions.whenTypeIs
import com.abbisea.caves.messages.Destroy
import com.abbisea.caves.messages.DropItem
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object LootDropper : BaseFacet<GameContext, Destroy>(Destroy::class) {

    override suspend fun receive(message: Destroy): Response {
        val (context, _, target) = message
        target.whenTypeIs<ItemHolder> { entity ->
            entity.inventory.items.forEach { item ->
                entity.receiveMessage(DropItem(context, entity, item, entity.position))
            }
        }
        return Pass
    }
}