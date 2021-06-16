package com.abbisea.caves.attributes

import com.abbisea.caves.extensions.toStringProperty
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.cobalt.databinding.api.binding.bindPlusWith
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component

data class CombatStats(
    val maxHpProperty: Property<Int>,
    val hpProperty: Property<Int> = createPropertyFrom(maxHpProperty.value),
    val attackValueProperty: Property<Int>,
    val defenseValueProperty: Property<Int>
) : BaseAttribute(), DisplayableAttribute {

    companion object {
        fun create(maxHp: Int, hp: Int = maxHp, attackValue: Int, defenseValue: Int) =
            CombatStats(
                maxHpProperty = createPropertyFrom(maxHp),
                hpProperty = createPropertyFrom(hp),
                attackValueProperty = createPropertyFrom(attackValue),
                defenseValueProperty = createPropertyFrom(defenseValue)
            )
    }

    val maxHp: Int by maxHpProperty.asDelegate()
    var hp: Int by hpProperty.asDelegate()
    val attackValue: Int by attackValueProperty.asDelegate()
    val defenseValue: Int by defenseValueProperty.asDelegate()

    override fun toComponent(width: Int): Component = Components.vbox()
        .withSize(width, 5)
        .build().apply {
            val hpLabel = Components.label()
                .withSize(width, 1)
                .build()

            val attackLabel = Components.label()
                .withSize(width, 1)
                .build()

            val defenseLabel = Components.label()
                .withSize(width, 1)
                .build()

            hpLabel.textProperty.updateFrom(
                createPropertyFrom("HP: ")
                    .bindPlusWith(hpProperty.toStringProperty())
                    .bindPlusWith("/".toProperty())
                    .bindPlusWith(maxHpProperty.toStringProperty())
            )

            attackLabel.textProperty.updateFrom(
                createPropertyFrom("Atk: ")
                    .bindPlusWith(attackValueProperty.toStringProperty())
            )

            defenseLabel.textProperty.updateFrom(
                createPropertyFrom("Def: ")
                    .bindPlusWith(defenseValueProperty.toStringProperty())
            )

            addComponent(
                Components.textBox(width)
                    .addHeader("Combat Stats")
            )

            addComponent(hpLabel)
            addComponent(attackLabel)
            addComponent(defenseLabel)
        }
}