package com.abbisea.caves.builders

import com.abbisea.caves.blocks.GameBlock
import com.abbisea.caves.extensions.sameLevelNeighboursShuffled
import com.abbisea.caves.world.World
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

class WorldBuilder(private val worldSize: Size3D) {
    private val width  = worldSize.xLength
    private val height = worldSize.zLength
    private var blocks = mutableMapOf<Position3D, GameBlock>()

    fun makeCaves(): WorldBuilder = randomiseTiles().smooth(8)

    fun build(visibleSize: Size3D) = World(blocks, visibleSize, worldSize)

    private fun randomiseTiles(): WorldBuilder {
        forAllPositions { pos ->
            blocks[pos] = if (Math.random() < 0.5) GameBlockFactory.floor()
            else GameBlockFactory.wall()
        }
        return this
    }

    private fun smooth(iterations: Int): WorldBuilder {
        val newBlocks = mutableMapOf<Position3D, GameBlock>()
        repeat(iterations) {
            forAllPositions { pos ->
                val (x, y, z) = pos
                var floors = 0
                var rocks = 0
                pos.sameLevelNeighboursShuffled().plus(pos).forEach { neighbour ->
                    blocks.whenPresent(neighbour) { block ->
                        if (block.isFloor) floors++
                        else rocks++
                    }
                }
                newBlocks[Position3D.create(x, y, z)] =
                    if (floors >= rocks) GameBlockFactory.floor() else GameBlockFactory.wall()
            }
            blocks = newBlocks
        }
        return this
    }

    private fun forAllPositions(fn: (Position3D) -> Unit) {
        worldSize.fetchPositions().forEach(fn)
    }

    private fun MutableMap<Position3D, GameBlock>.whenPresent(pos: Position3D, fn: (GameBlock) -> Unit) {
        this[pos]?.let(fn)
    }
}