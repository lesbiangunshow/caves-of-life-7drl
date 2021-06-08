package com.abbisea.caves.builders

import com.abbisea.caves.GameConfig
import com.abbisea.caves.GameConfig.FUNGI_PER_LEVEL
import com.abbisea.caves.GameConfig.LOG_AREA_HEIGHT
import com.abbisea.caves.GameConfig.SIDEBAR_WIDTH
import com.abbisea.caves.GameConfig.WINDOW_HEIGHT
import com.abbisea.caves.GameConfig.WINDOW_WIDTH
import com.abbisea.caves.GameConfig.WORLD_SIZE
import com.abbisea.caves.attributes.types.Player
import com.abbisea.caves.world.Game
import com.abbisea.caves.world.GameEntity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
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
        addFungi()
        return Game.create(world, player)
    }

    private fun prepareWorld() = also {
        world.scrollUpBy(world.actualSize.zLength)
    }

    private fun addPlayer(): GameEntity<Player> =
        EntityFactory.newPlayer().addToWorld(
            atLevel = GameConfig.DUNGEON_LEVELS - 1,
            atArea = world.visibleSize.to2DSize()
        )

    private fun addFungi() = also {
        repeat(world.actualSize.zLength) { level ->
            repeat(FUNGI_PER_LEVEL) {
                EntityFactory.newFungus().addToWorld(level)
            }
        }
    }

    private fun <T : EntityType> GameEntity<T>.addToWorld(
        atLevel: Int,
        atArea: Size = world.actualSize.to2DSize()
    ): GameEntity<T> {
        world.addAtEmptyPosition(
            this, offset = Position3D.defaultPosition().withZ(atLevel), Size3D.from2DSize(atArea)
        )
        return this
    }
}