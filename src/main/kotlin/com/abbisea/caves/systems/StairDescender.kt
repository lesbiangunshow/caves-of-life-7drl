package com.abbisea.caves.systems

import com.abbisea.caves.attributes.types.Player
import com.abbisea.caves.attributes.types.StairsDown
import com.abbisea.caves.blocks.GameBlock
import com.abbisea.caves.extensions.position
import com.abbisea.caves.functions.logGameEvent
import com.abbisea.caves.messages.MoveDown
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object StairDescender : BaseFacet<GameContext, MoveDown>(MoveDown::class) {

    override suspend fun receive(message: MoveDown): Response {
        val (context, source) = message
        val world = context.world
        val position = source.position
        world.fetchBlockAt(position).map { block ->
            if (block.hasStairsDown) {
                world.moveEntity(source, position.withRelativeZ(-1))
                if (source.type == Player) {
                    logGameEvent("You move down one level...", this)
                    world.scrollOneDown()
                }
            } else {
                if (source.type == Player) logGameEvent(
                    "You search for a trapdoor, but you find nothing.",
                    this
                )
            }
        }
        return Consumed
    }

    private val GameBlock.hasStairsDown: Boolean
        get() = this.entities.any { it.type == StairsDown }
}