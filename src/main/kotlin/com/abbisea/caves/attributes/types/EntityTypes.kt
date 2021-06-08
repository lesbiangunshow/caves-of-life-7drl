package com.abbisea.caves.attributes.types

import org.hexworks.amethyst.api.base.BaseEntityType

object FOW: BaseEntityType(
    name = "Fog of War"
)

object Player : BaseEntityType(
    name = "player"
), Combatant

object Wall : BaseEntityType(
    name = "wall"
)

object Fungus : BaseEntityType(
    name = "fungus"
), Combatant

object StairsUp : BaseEntityType(
    name = "stairs up"
)

object StairsDown : BaseEntityType(
    name = "stairs down"
)