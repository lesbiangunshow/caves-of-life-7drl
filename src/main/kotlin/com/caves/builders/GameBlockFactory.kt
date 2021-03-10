package com.caves.builders

import com.caves.blocks.GameBlock

object GameBlockFactory {

    fun floor() = GameBlock(GameTileRepository.FLOOR)

    fun wall() = GameBlock(GameTileRepository.WALL)
}