package com.caves.world

import com.caves.GameConfig.GAME_AREA_SIZE
import com.caves.GameConfig.WORLD_SIZE
import com.caves.builders.WorldBuilder
import org.hexworks.zircon.api.data.Size3D

class Game(val world: World) {

    companion object {

        fun create(
            worldSize: Size3D = WORLD_SIZE,
            visibleSize: Size3D = GAME_AREA_SIZE
        ) = Game(
            WorldBuilder(worldSize)
                .makeCaves()
                .build(visibleSize)
        )
    }
}