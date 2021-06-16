package com.abbisea.caves.systems

import com.abbisea.caves.extensions.position
import com.abbisea.caves.messages.MoveCamera
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object CameraMover : BaseFacet<GameContext, MoveCamera>(MoveCamera::class) {

    override suspend fun receive(message: MoveCamera): Response {
        val (context, source, previousPosition) = message
        with(context.world) {
            val screenPos = source.position - visibleOffset
            val halfHeight = visibleSize.yLength / 2
            val halfWidth = visibleSize.xLength / 2
            val currentPosition = source.position
            when {
                previousPosition.y > currentPosition.y && screenPos.y < halfHeight -> scrollOneBackward()
                previousPosition.y < currentPosition.y && screenPos.y > halfHeight -> scrollOneForward()
                previousPosition.x > currentPosition.x && screenPos.x < halfWidth -> scrollOneLeft()
                previousPosition.x < currentPosition.x && screenPos.x > halfWidth -> scrollOneRight()
                else -> {
                }
            }
        }
        return Consumed
    }
}