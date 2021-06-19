package com.abbisea.caves.attributes

import com.abbisea.caves.attributes.types.*
import com.abbisea.caves.extensions.GameCombatItem
import com.abbisea.caves.extensions.GameEntity
import com.abbisea.caves.extensions.whenTypeIs
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component

class Equipment(
    initialWeapon: GameEntity<Weapon>,
    initialArmour: GameEntity<Armour>
) : BaseAttribute(), DisplayableAttribute {

    private val weaponProperty: Property<GameEntity<Weapon>> = createPropertyFrom(initialWeapon)
    private val armourProperty: Property<GameEntity<Armour>> = createPropertyFrom(initialArmour)

    val attackValue: Int                                                                            // 2
        get() = weaponProperty.value.attackValue + armourProperty.value.attackValue

    val defenseValue: Int
        get() = weaponProperty.value.defenseValue + armourProperty.value.defenseValue

    val armourName: String
        get() = armourProperty.value.name

    val weaponName: String
        get() = weaponProperty.value.name

    val weapon: GameEntity<Weapon> by weaponProperty.asDelegate()
    val armour: GameEntity<Armour> by armourProperty.asDelegate()

    private val weaponStats: String
        get() = " A: ${weapon.attackValue} D: ${weapon.defenseValue}"

    private val armourStats: String
        get() = " A: ${armour.attackValue} D: ${armour.defenseValue}"

    fun equip(inventory: Inventory, combatItem: GameCombatItem): GameCombatItem {
        combatItem.whenTypeIs<Weapon> { return equipWeapon(inventory, it) }
        combatItem.whenTypeIs<Armour> { return equipArmour(inventory, it) }
        throw IllegalStateException("Combat item is not Weapon or Armour.")
    }

    private fun equipWeapon(inventory: Inventory, newWeapon: GameEntity<Weapon>): GameCombatItem {
        val oldWeapon = weapon
        inventory.removeItem(newWeapon)
        inventory.addItem(oldWeapon)
        weaponProperty.value = newWeapon
        return oldWeapon
    }

    private fun equipArmour(inventory: Inventory, newArmour: GameEntity<Armour>): GameCombatItem {
        val oldArmour = armour
        inventory.removeItem(newArmour)
        inventory.addItem(oldArmour)
        armourProperty.value = newArmour
        return oldArmour
    }

    override fun toComponent(width: Int): Component {
        val weaponIcon = Components.icon().withIcon(weapon.iconTile).build()
        val weaponNameLabel = Components.label()
            .withText(weaponName)
            .withSize(width - 2, 1)
            .build()
        val weaponStatsLabel = Components.label()
            .withText(weaponStats)
            .withSize(width - 1, 1)
            .build()

        val armourIcon = Components.icon().withIcon(armour.iconTile).build()
        val armourNameLabel = Components.label()
            .withText(armourName)
            .withSize(width - 2, 1)
            .build()
        val armourStatsLabel = Components.label()
            .withText(armourStats)
            .withSize(width - 1, 1)
            .build()

        weaponProperty.onChange {
            val newWeapon = it.newValue
            weaponIcon.iconProperty.value = newWeapon.iconTile
            weaponNameLabel.textProperty.value = newWeapon.name
            weaponStatsLabel.textProperty.value = weaponStats
        }
        armourProperty.onChange {
            val newArmour = it.newValue
            armourIcon.iconProperty.value = newArmour.iconTile
            armourNameLabel.textProperty.value = newArmour.name
            armourStatsLabel.textProperty.value = armourStats
        }

        return Components.textBox(width)
            .addHeader("Weapon", withNewLine = false)
            .addInlineComponent(weaponIcon)
            .addInlineComponent(weaponNameLabel)
            .commitInlineElements()
            .addInlineComponent(weaponStatsLabel)
            .commitInlineElements()
            .addNewLine()
            .addHeader("Armour", withNewLine = false)
            .addInlineComponent(armourIcon)
            .addInlineComponent(armourNameLabel)
            .commitInlineElements()
            .addInlineComponent(armourStatsLabel)
            .commitInlineElements()
            .build()
    }
}