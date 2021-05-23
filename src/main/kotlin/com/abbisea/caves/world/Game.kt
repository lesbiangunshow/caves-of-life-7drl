package com.abbisea.caves.world

import com.abbisea.caves.attributes.types.Player

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