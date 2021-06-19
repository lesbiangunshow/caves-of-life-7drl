package com.abbisea.caves.world

import com.abbisea.caves.attributes.Vision
import com.abbisea.caves.blocks.GameBlock
import com.abbisea.caves.extensions.AnyGameEntity
import com.abbisea.caves.extensions.blocksVision
import com.abbisea.caves.extensions.position
import org.hexworks.amethyst.api.Engine
import org.hexworks.amethyst.internal.TurnBasedEngine
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.builder.game.GameAreaBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.shape.EllipseFactory
import org.hexworks.zircon.api.shape.LineFactory
import org.hexworks.zircon.api.uievent.UIEvent
import kotlin.math.abs
import kotlin.math.pow

class World(
    startingBlocks: Map<Position3D, GameBlock>,
    visibleSize: Size3D,
    actualSize: Size3D
) : GameArea<Tile, GameBlock> by GameAreaBuilder.newBuilder<Tile, GameBlock>()
    .withVisibleSize(visibleSize)
    .withActualSize(actualSize)
    .build() {

    private val engine: TurnBasedEngine<GameContext> = Engine.create()

    init {
        startingBlocks.forEach { (pos, block) ->
            setBlockAt(pos, block)
            block.entities.forEach { entity ->
                engine.addEntity(entity)
                entity.position = pos
            }
        }
    }

    fun update(screen: Screen, uiEvent: UIEvent, game: Game) {
        engine.executeTurn(
            GameContext(
                world = this,
                screen = screen,
                uiEvent = uiEvent,
                player = game.player
            )
        )
    }

    fun addWorldEntity(entity: AnyGameEntity) {
        engine.addEntity(entity)
    }

    fun addEntity(entity: AnyGameEntity, position: Position3D) {
        entity.position = position
        engine.addEntity(entity)
        fetchBlockAt(position).map {
            it.addEntity(entity)
        }
    }

    fun removeEntity(entity: AnyGameEntity) {
        fetchBlockAt(entity.position).map {
            it.removeEntity(entity)
        }
        engine.removeEntity(entity)
        entity.position = Position3D.unknown()
    }

    fun addAtEmptyPosition(
        entity: AnyGameEntity,
        offset: Position3D = Position3D.create(0, 0, 0),
        size: Size3D = actualSize
    ): Boolean {
        return findEmptyLocationWithin(offset, size).fold(
            whenEmpty = {
                false
            },
            whenPresent = { location ->
                addEntity(entity, location)
                true
            }
        )
    }

    fun moveEntity(entity: AnyGameEntity, position: Position3D): Boolean {
        val oldBlock = fetchBlockAt(entity.position)
        val newBlock = fetchBlockAt(position)
        return if (bothBlocksPresent(oldBlock, newBlock)) {
            oldBlock.get().removeEntity(entity)
            entity.position = position
            newBlock.get().addEntity(entity)
            true
        } else false
    }

    private fun bothBlocksPresent(
        oldBlock: Maybe<GameBlock>,
        newBlock: Maybe<GameBlock>
    ) =
        oldBlock.isPresent && newBlock.isPresent

    fun findEmptyLocationWithin(offset: Position3D, size: Size3D): Maybe<Position3D> {
        var position = Maybe.empty<Position3D>()
        val maxTries = 10
        var currentTry = 0
        while (position.isPresent.not() && currentTry < maxTries) {
            val pos = Position3D.create(
                x = (Math.random() * size.xLength).toInt() + offset.x,
                y = (Math.random() * size.yLength).toInt() + offset.y,
                z = (Math.random() * size.zLength).toInt() + offset.z
            )
            fetchBlockAt(pos).map {
                if (it.isEmptyFloor) {
                    position = Maybe.of(pos)
                }
            }
            currentTry++
        }
        return position
    }

    fun isVisionBlockedAt(pos: Position3D): Boolean =
        fetchBlockAt(pos).fold(whenEmpty = { false }, whenPresent = {
            it.entities.any(AnyGameEntity::blocksVision)
        })

    fun findVisiblePositionsFor(entity: AnyGameEntity): Iterable<Position> {
        val centerPos = entity.position.to2DPosition()
        return entity.findAttribute(Vision::class).map { (radius) ->
            EllipseFactory.buildEllipse(
                fromPosition = centerPos,
                toPosition = centerPos.withRelativeX(radius).withRelativeY(radius)
            ).positions.flatMap { ringPos ->
                val result = mutableListOf<Position>()
                val iter = LineFactory.buildLine(centerPos, ringPos).iterator()
                do {
                    val next = iter.next()
                    result.add(next)
                } while (iter.hasNext() &&
                    isVisionBlockedAt(Position3D.from2DPosition(next, entity.position.z)).not()
                )
                result
            }
        }.orElse(listOf())
    }

    fun findPath(
        looker: AnyGameEntity,
        target: AnyGameEntity
    ): List<Position> {
        var result = listOf<Position>()
        looker.findAttribute(Vision::class).map { (radius) ->
            val level = looker.position.z
            if (looker.position.isWithinRangeOf(target.position, radius)) {
                val path = LineFactory.buildLine(looker.position.to2DPosition(), target.position.to2DPosition())
                if (path.none { isVisionBlockedAt(it.toPosition3D(level)) }) {
                    result = path.positions.toList().drop(1)
                }
            }
        }
        return result
    }

    private fun Position3D.isWithinRangeOf(other: Position3D, radius: Int): Boolean =
        isUnknown.not()
                && other.isUnknown.not()
                && z == other.z
                && (x - other.x).toDouble().pow(2) + abs(y - other.y).toDouble().pow(2) <= radius.toDouble().pow(2)
}