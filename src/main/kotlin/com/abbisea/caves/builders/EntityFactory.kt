package com.abbisea.caves.builders

import com.abbisea.caves.attributes.*
import com.abbisea.caves.attributes.flags.BlockOccupier
import com.abbisea.caves.attributes.flags.VisionBlocker
import com.abbisea.caves.attributes.types.*
import com.abbisea.caves.builders.GameTileRepository.PLAYER
import com.abbisea.caves.extensions.GameEntity
import com.abbisea.caves.messages.Attack
import com.abbisea.caves.messages.Dig
import com.abbisea.caves.systems.*
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.data.Tile
import kotlin.random.Random

fun <T : EntityType> newGameEntityOfType(
    type: T,
    init: EntityBuilder<T, GameContext>.() -> Unit
) = newEntityOfType(type, init)

object EntityFactory {

    fun newFogOfWar() = newGameEntityOfType(FOW) {
        behaviors(FogOfWar)
    }

    fun newPlayer() = newGameEntityOfType(Player) {
        attributes(
            BlockOccupier,
            EntityPosition(),
            EntityTile(PLAYER),
            EntityActions(Dig::class, Attack::class),
            CombatStats.create(
                maxHp = 100,
                attackValue = 10,
                defenseValue = 5
            ),
            Vision(9),
            Inventory(10),
            Equipment(
                initialWeapon = newClub(),
                initialArmour = newJacket()
            ),
            EnergyLevel(1000, 1000)
        )
        behaviors(InputReceiver, EnergyExpender)
        facets(
            Movable,
            CameraMover,
            StairClimber,
            StairDescender,
            Attackable,
            ItemPicker,
            ItemDropper,
            InventoryInspector,
            EnergyExpender,
            Destructible,
            DigestiveSystem
        )
    }

    fun newWall() = newGameEntityOfType(Wall) {
        attributes(EntityPosition(), BlockOccupier, EntityTile(GameTileRepository.WALL), VisionBlocker)
        facets(Diggable)
    }

    fun newStairsup() = newGameEntityOfType(StairsUp) {
        attributes(
            EntityTile(GameTileRepository.STAIRS_UP),
            EntityPosition()
        )
    }

    fun newStairsDown() = newGameEntityOfType(StairsDown) {
        attributes(
            EntityTile(GameTileRepository.STAIRS_DOWN),
            EntityPosition()
        )
    }

    fun newFungus(fungusSpread: FungusSpread = FungusSpread()) = newGameEntityOfType(Fungus) {
        attributes(
            BlockOccupier,
            EntityPosition(),
            EntityTile(GameTileRepository.FUNGUS),
            fungusSpread,
            CombatStats.create(
                maxHp = 10,
                attackValue = 0,
                defenseValue = 0
            )
        )
        facets(Attackable, Destructible)
        behaviors(FungusGrowth)
    }

    fun newBat() = newGameEntityOfType(Bat) {
        attributes(
            BlockOccupier,
            EntityPosition(),
            EntityTile(GameTileRepository.BAT),
            CombatStats.create(
                maxHp = 5,
                attackValue = 2,
                defenseValue = 1
            ),
            EntityActions(Attack::class),
            Inventory(1).apply {
                addItem(newBatMeat())
            }
        )
        facets(Movable, Attackable, ItemDropper, LootDropper, Destructible)
        behaviors(Wanderer)
    }

    fun newBatMeat() = newGameEntityOfType(BatMeat) {
        attributes(
            ItemIcon(
                Tile.newBuilder()
                    .withName("Meatball")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            NutritionalValue(750),
            EntityPosition(),
            EntityTile(GameTileRepository.BAT_MEAT)
        )
    }

    fun newZircon() = newGameEntityOfType(Zircon) {
        attributes(
            ItemIcon(
                Tile.newBuilder()
                    .withName("white gem")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            EntityPosition(),
            EntityTile(GameTileRepository.ZIRCON)
        )
    }

    fun newRandomWeapon(): GameEntity<Weapon> = when (Random.nextInt(3)) {
        0 -> newDagger()
        1 -> newSword()
        else -> newStaff()
    }

    fun newRandomArmor(): GameEntity<Armour> = when (Random.nextInt(3)) {
        0 -> newLightArmor()
        1 -> newMediumArmor()
        else -> newHeavyArmor()
    }

    fun newDagger() = newGameEntityOfType(Dagger) {
        attributes(
            ItemIcon(
                Tile.newBuilder()
                    .withName("Dagger")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            EntityPosition(),
            ItemCombatStats(
                attackValue = 4,
                combatItemType = "Weapon"
            ),
            EntityTile(GameTileRepository.DAGGER)
        )
    }

    fun newSword() = newGameEntityOfType(Sword) {
        attributes(
            ItemIcon(
                Tile.newBuilder()
                    .withName("Short sword")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            EntityPosition(),
            ItemCombatStats(
                attackValue = 6,
                combatItemType = "Weapon"
            ),
            EntityTile(GameTileRepository.SWORD)
        )
    }

    fun newStaff() = newGameEntityOfType(Staff) {
        attributes(
            ItemIcon(
                Tile.newBuilder()
                    .withName("staff")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            EntityPosition(),
            ItemCombatStats(
                attackValue = 4,
                defenseValue = 2,
                combatItemType = "Weapon"
            ),
            EntityTile(GameTileRepository.STAFF)
        )
    }

    fun newLightArmor() = newGameEntityOfType(LightArmor) {
        attributes(
            ItemIcon(
                Tile.newBuilder()
                    .withName("Leather armor")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            EntityPosition(),
            ItemCombatStats(
                defenseValue = 2,
                combatItemType = "Armor"
            ),
            EntityTile(GameTileRepository.LIGHT_ARMOR)
        )
    }

    fun newMediumArmor() = newGameEntityOfType(MediumArmor) {
        attributes(
            ItemIcon(
                Tile.newBuilder()
                    .withName("Chain mail")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            EntityPosition(),
            ItemCombatStats(
                defenseValue = 3,
                combatItemType = "Armor"
            ),
            EntityTile(GameTileRepository.MEDIUM_ARMOR)
        )
    }

    fun newHeavyArmor() = newGameEntityOfType(HeavyArmor) {
        attributes(
            ItemIcon(
                Tile.newBuilder()
                    .withName("Plate mail")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            EntityPosition(),
            ItemCombatStats(
                defenseValue = 4,
                combatItemType = "Armor"
            ),
            EntityTile(GameTileRepository.HEAVY_ARMOR)
        )
    }

    fun newClub() = newGameEntityOfType(Club) {
        attributes(
            ItemCombatStats(combatItemType = "Weapon"),
            EntityTile(GameTileRepository.CLUB),
            EntityPosition(),
            ItemIcon(
                Tile.newBuilder()
                    .withName("Club")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            )
        )
    }

    fun newJacket() = newGameEntityOfType(Jacket) {
        attributes(
            ItemCombatStats(combatItemType = "Armor"),
            EntityTile(GameTileRepository.JACKET),
            EntityPosition(),
            ItemIcon(
                Tile.newBuilder()
                    .withName("Leather jacket")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            )
        )
    }
}