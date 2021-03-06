package com.abbisea.caves.extensions

import com.abbisea.caves.attributes.types.CombatItem
import com.abbisea.caves.attributes.types.EquipmentHolder
import com.abbisea.caves.attributes.types.Item
import com.abbisea.caves.attributes.types.ItemHolder
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

typealias GameEntity<T> = Entity<T, GameContext>
typealias  AnyGameEntity = GameEntity<EntityType>
typealias GameItem = GameEntity<Item>
typealias GameCombatItem = GameEntity<CombatItem>
typealias GameItemHolder = GameEntity<ItemHolder>
typealias GameEquipmentHolder = GameEntity<EquipmentHolder>
typealias GameMessage = Message<GameContext>