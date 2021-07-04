package com.abbisea.caves.attributes.types

import org.hexworks.amethyst.api.base.BaseEntityType

object FOW : BaseEntityType(
    name = "Fog of War"
)

object Player : BaseEntityType(
    name = "player"
), Combatant, ItemHolder, EquipmentHolder, EnergyUser, ExperienceGainer

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
), Combatant, ItemHolder

object Zombie : BaseEntityType(
    name = "zombie"
), Combatant, ItemHolder

object BatMeat : BaseEntityType(
    name = "Bat meat",
    description = "Stringy bat meat. It is edible, but not tasty."
), Food

object Dagger : BaseEntityType(
    name = "Rusty Dagger",
    description = "A small, rusty dagger made of some metal alloy."
), Weapon

object Sword : BaseEntityType(
    name = "Iron Sword",
    description = "A shiny sword made of iron. It is a two-handed weapon."
), Weapon

object Staff : BaseEntityType(
    name = "Wooden Staff",
    description = "A staff made of birch wood. It has seen some use."
), Weapon

object LightArmor : BaseEntityType(
    name = "Leather Tunic",
    description = "A tunic made of rugged leather. It is very comfortable."
), Armour

object MediumArmor : BaseEntityType(
    name = "Chainmail",
    description = "A sturdy chainmail armor made of interlocking iron chains."
), Armour

object HeavyArmor : BaseEntityType(
    name = "Platemail",
    description = "A heavy and shiny platemail armor made of bronze."
), Armour

object Club : BaseEntityType(
    name = "Club",
    description = "A wooden club. It doesn't give you an edge over your opponent (haha)."
), Weapon

object Jacket : BaseEntityType(
    name = "Leather jacket",
    description = "Dirty and rugged jacket made of leather."
), Armour
