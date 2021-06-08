package com.abbisea.caves.systems

import com.abbisea.caves.attributes.types.Player
import com.abbisea.caves.attributes.types.StairsUp
import com.abbisea.caves.blocks.GameBlock
import com.abbisea.caves.extensions.position
import com.abbisea.caves.functions.logGameEvent
import com.abbisea.caves.messages.MoveUp
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object StairClimber : BaseFacet<GameContext, MoveUp>(MoveUp::class) {

    override suspend fun receive(message: MoveUp): Response {
        val (context, source) = message
        val world = context.world
        val position = source.position
        world.fetchBlockAt(position).map { block ->
            if (block.hasStairsUp) {
                world.moveEntity(source, position.withRelativeZ(1))
                if (source.type == Player) {
                    logGameEvent("You move up one level...", StairClimber)
                    world.scrollOneUp()
                }
            } else {
                if (source.type == Player) logGameEvent(
                    "You jump and try to reach the ceiling, You fail.",
                    StairClimber
                )
            }
        }
        return Consumed
    }

    private val GameBlock.hasStairsUp: Boolean
        get() = this.entities.any { it.type == StairsUp }
}