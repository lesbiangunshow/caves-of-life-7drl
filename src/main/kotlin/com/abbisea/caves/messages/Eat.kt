package com.abbisea.caves.messages

import com.abbisea.caves.attributes.types.EnergyUser
import com.abbisea.caves.attributes.types.Food
import com.abbisea.caves.extensions.GameEntity
import com.abbisea.caves.world.GameContext

data class Eat(
    override val context: GameContext,
    override val source: GameEntity<EnergyUser>,
    override val target: GameEntity<Food>
) : EntityAction<EnergyUser, Food>