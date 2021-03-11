package com.caves.builders

import com.caves.GameConfig
import com.caves.GameConfig.LOG_AREA_HEIGHT
import com.caves.GameConfig.SIDEBAR_WIDTH
import com.caves.GameConfig.WINDOW_HEIGHT
import com.caves.GameConfig.WINDOW_WIDTH
import com.caves.GameConfig.WORLD_SIZE
import com.caves.attributes.types.Player
import com.caves.world.Game
import com.caves.world.GameEntity
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

class GameBuilder(val worldSize: Size3D) {

    companion object {

        fun create() = GameBuilder(
            worldSize = WORLD_SIZE
        ).buildGame()
    }

    private val visibleSize = Size3D.create(
        xLength = WINDOW_WIDTH - SIDEBAR_WIDTH,
        yLength = WINDOW_HEIGHT - LOG_AREA_HEIGHT,
        zLength = 1
    )

    val world = WorldBuilder(worldSize)
        .makeCaves()
        .build(visibleSize = visibleSize)

    fun buildGame(): Game {
        prepareWorld()
        val player = addPlayer()

        return Game.create(world, player)
    }

    private fun prepareWorld() = also {
        world.scrollUpBy(world.actualSize.zLength)
    }

    private fun addPlayer(): GameEntity<Player> =
        EntityFactory.newPlayer().also {
            world.addAtEmptyPosition(
                it,
                offset = Position3D.create(0, 0, GameConfig.DUNGEON_LEVELS - 1),
                size = world.visibleSize.copy(zLength = 0)
            )
        }
}