package com.abbisea.caves.view.dialog

import com.abbisea.caves.attributes.CombatStats
import com.abbisea.caves.attributes.Vision
import com.abbisea.caves.attributes.types.Player
import com.abbisea.caves.extensions.GameEntity
import com.abbisea.caves.extensions.tryToFindAttribute
import com.abbisea.caves.functions.logGameEvent
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

class LevelUpDialog(
    screen: Screen,
    player: GameEntity<Player>
) : Dialog(screen, false) {

    override val container = Components.vbox()
        .withDecorations(box(title = "Ding!", boxType = BoxType.TOP_BOTTOM_DOUBLE))
        .withSize(30, 15)
        .build().apply {
            val stats = player.tryToFindAttribute(CombatStats::class)
            val vision = player.tryToFindAttribute(Vision::class)

            addComponent(
                Components.textBox(27)
                    .addHeader("Congratulations, you leveled up!")
                    .addParagraph("Pick an improvement from the options below:")
            )

            addComponent(Components.button()
                .withText("Max HP")
                .build().apply {
                    onActivated {
                        stats.maxHpProperty.value += 10
                        logGameEvent("You look healthier.", this)
                        root.close(EmptyModalResult)
                    }
                })

            addComponent(Components.button()
                .withText("Attack")
                .build().apply {
                    onActivated {
                        stats.attackValueProperty.value += 2
                        logGameEvent("You look stronger.", this)
                        root.close(EmptyModalResult)
                    }
                })

            addComponent(Components.button()
                .withText("Defense")
                .build().apply {
                    onActivated {
                        stats.defenseValueProperty.value += 2
                        logGameEvent("You look tougher.", this)
                        root.close(EmptyModalResult)
                    }
                })

            addComponent(Components.button()
                .withText("Vision")
                .build().apply {
                    onActivated {
                        vision.radius++
                        logGameEvent("You got eye enlargement surgery so you can see your family better.", this)
                        root.close(EmptyModalResult)
                    }
                })
        }
}