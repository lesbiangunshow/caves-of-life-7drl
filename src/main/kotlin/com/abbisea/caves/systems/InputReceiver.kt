package com.abbisea.caves.systems

import com.abbisea.caves.attributes.types.Player
import com.abbisea.caves.extensions.GameEntity
import com.abbisea.caves.extensions.position
import com.abbisea.caves.messages.*
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent

object InputReceiver : BaseBehavior<GameContext>() {

    private val logger = LoggerFactory.getLogger(this::class)

    override suspend fun update(entity: Entity<EntityType, GameContext>, context: GameContext): Boolean {
        val (_, _, uiEvent, player) = context
        val currentPos = player.position
        if (uiEvent is KeyboardEvent) {
            when (uiEvent.code) {
                KeyCode.KEY_W -> player.moveTo(currentPos.withRelativeY(-1), context)
                KeyCode.KEY_A -> player.moveTo(currentPos.withRelativeX(-1), context)
                KeyCode.KEY_S -> player.moveTo(currentPos.withRelativeY(1), context)
                KeyCode.KEY_D -> player.moveTo(currentPos.withRelativeX(1), context)
                KeyCode.KEY_R -> player.moveUp(context)
                KeyCode.KEY_F -> player.moveDown(context)
                KeyCode.KEY_P -> player.pickItemUp(currentPos, context)
                KeyCode.KEY_I -> player.inspectInventory(currentPos, context)
                else -> logger.debug("UI Event ($uiEvent) does not have a corresponding command, it is ignored.")
            }
        }
        return true
    }

    private suspend fun GameEntity<Player>.inspectInventory(position: Position3D, context: GameContext) {
        receiveMessage(InspectInventory(context, this, position))
    }

    private suspend fun GameEntity<Player>.pickItemUp(position: Position3D, context: GameContext) {
        receiveMessage(PickItemUp(context, this, position))
    }

    private suspend fun GameEntity<Player>.moveTo(position: Position3D, context: GameContext) {
        receiveMessage(MoveTo(context, this, position))
    }

    private suspend fun GameEntity<Player>.moveUp(context: GameContext) {
        receiveMessage(MoveUp(context, this))
    }

    private suspend fun GameEntity<Player>.moveDown(context: GameContext) {
        receiveMessage(MoveDown(context, this))
    }
}