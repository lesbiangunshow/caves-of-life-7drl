package com.caves.world

import com.caves.GameConfig.GAME_AREA_SIZE
import com.caves.GameConfig.WORLD_SIZE
import com.caves.attributes.types.Player
import com.caves.builders.WorldBuilder
import org.hexworks.zircon.api.data.Size3D

class Game(
    val world: World,
    val player: GameEntity<Player>
    ) {

    companion object {

        fun create(
            world: World,
            player: GameEntity<Player>
        ) = Game(
            world,
            player
        )
    }
}