package com.abbisea.caves.attributes.types

import org.hexworks.amethyst.api.base.BaseEntityType

object FOW : BaseEntityType(
    name = "Fog of War"
)

object Player : BaseEntityType(
    name = "player"
), Combatant, ItemHolder

object Wall : BaseEntityType(
    name = "wall"
)

object StairsUp : BaseEntityType(
    name = "stairs up"
)

object StairsDown : BaseEntityType(
    name = "stairs down"
)

object Zircon : BaseEntityType(
    name = "Zircon",
    description = "A small piece of Zircon. Its value is unfathomable."
), Item

object Fungus : BaseEntityType(
    name = "fungus"
), Combatant

object Bat : BaseEntityType(
    name = "bat"
), Combatant