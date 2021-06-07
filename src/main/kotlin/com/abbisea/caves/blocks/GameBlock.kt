package com.abbisea.caves.blocks

import com.abbisea.caves.builders.GameTileRepository.FLOOR
import com.abbisea.caves.builders.GameTileRepository.PLAYER
import com.abbisea.caves.builders.GameTileRepository.WALL
import com.abbisea.caves.extensions.occupiesBlock
import com.abbisea.caves.extensions.tile
import com.abbisea.caves.world.AnyGameEntity
import com.abbisea.caves.world.GameEntity
import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseBlock

class GameBlock(
    private var defaultTile: Tile = FLOOR,
    private val currentEntities: MutableList<AnyGameEntity> = mutableListOf()
) : BaseBlock<Tile>(
    emptyTile = Tile.empty(),
    tiles = persistentMapOf(BlockTileType.CONTENT to defaultTile)
) {

    companion object {
        fun createWith(entity: AnyGameEntity) = GameBlock(
            currentEntities = mutableListOf(entity)
        )
    }

    init {
        updateContent()
    }

    val isFloor: Boolean
        get() = defaultTile == FLOOR

    val isWall: Boolean
        get() = defaultTile == WALL

    val isEmptyFloor: Boolean
        get() = currentEntities.isEmpty()

    val entities: Iterable<GameEntity<EntityType>>
        get() = currentEntities.toList()

    fun addEntity(entity: GameEntity<EntityType>) {
        currentEntities.add(entity)
        updateContent()
    }

    fun removeEntity(entity: GameEntity<EntityType>) {
        currentEntities.remove(entity)
        updateContent()
    }

    private fun updateContent() {
        val entityTiles = currentEntities.map { it.tile }
        content = when {
            entityTiles.contains(PLAYER) -> PLAYER
            entityTiles.isNotEmpty() -> entityTiles.first()
            else -> defaultTile
        }
    }

    val occupier: Maybe<AnyGameEntity>
        get() = Maybe.ofNullable(currentEntities.firstOrNull { it.occupiesBlock })

    val isOccupied: Boolean
        get() = occupier.isPresent
}