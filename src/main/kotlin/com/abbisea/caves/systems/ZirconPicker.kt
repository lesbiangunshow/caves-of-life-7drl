package com.abbisea.caves.systems

import com.abbisea.caves.attributes.ZirconCounter
import com.abbisea.caves.attributes.types.Zircon
import com.abbisea.caves.attributes.types.ZirconHolder
import com.abbisea.caves.attributes.types.zirconCounter
import com.abbisea.caves.extensions.whenTypeIs
import com.abbisea.caves.functions.logGameEvent
import com.abbisea.caves.messages.PickItemUp
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object ZirconPicker : BaseFacet<GameContext, PickItemUp>(PickItemUp::class, ZirconCounter::class) {

    override suspend fun receive(message: PickItemUp): Response {
        var response: Response = Pass
        val (context, itemHolder, position) = message
        val world = context.world
        world.findTopItem(position).map { item ->
            itemHolder.whenTypeIs<ZirconHolder> { zirconHolder ->
                if (item.type == Zircon) {
                    zirconHolder.zirconCounter.zirconCount++
                    world.removeEntity(item)
                    logGameEvent("${zirconHolder.name} picked up a Zircon!", this)
                    response = Consumed
                }
            }
        }
        return response
    }
}