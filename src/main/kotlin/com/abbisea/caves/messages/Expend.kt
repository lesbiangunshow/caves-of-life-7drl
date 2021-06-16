package com.abbisea.caves.messages

import com.abbisea.caves.attributes.types.EnergyUser
import com.abbisea.caves.extensions.GameEntity
import com.abbisea.caves.extensions.GameMessage
import com.abbisea.caves.world.GameContext

data class Expend(
    override val context: GameContext,
    override val source: GameEntity<EnergyUser>,
    val energy: Int
) : GameMessage
