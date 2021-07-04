package com.abbisea.caves.attributes

import com.abbisea.caves.extensions.toStringProperty
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.cobalt.databinding.api.binding.bindPlusWith
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.Components

data class Experience(
    val currentXPProperty: Property<Int> = createPropertyFrom(0),
    val currentLevelProperty: Property<Int> = createPropertyFrom(1)
) : BaseAttribute(), DisplayableAttribute {

    var currentXP by currentXPProperty.asDelegate()
    var currentLevel by currentLevelProperty.asDelegate()

    override fun toComponent(width: Int) = Components.vbox()
        .withSize(width, 3)
        .build().apply {
            val xpLabel = Components.label()
                .withSize(width, 1)
                .build()
            val levelLabel = Components.label()
                .withSize(width, 1)
                .build()

            xpLabel.textProperty.updateFrom(
                createPropertyFrom("XP:  ")
                    .bindPlusWith(currentXPProperty.toStringProperty())
            )

            levelLabel.textProperty.updateFrom(
                createPropertyFrom("Lvl: ")
                    .bindPlusWith(currentLevelProperty.toStringProperty())
            )

            addComponent(
                Components.textBox(width)
                    .addHeader("Experience", false)
            )
            addComponent(xpLabel)
            addComponent(levelLabel)
        }
}