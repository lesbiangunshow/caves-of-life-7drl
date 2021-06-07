package com.abbisea.caves.builders

import com.abbisea.caves.blocks.GameBlock

object GameBlockFactory {

    fun floor() = GameBlock(GameTileRepository.FLOOR)

    fun wall() = GameBlock.createWith(EntityFactory.newWall())

}