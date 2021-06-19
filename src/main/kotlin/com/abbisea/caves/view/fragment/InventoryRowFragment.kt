package com.abbisea.caves.view.fragment

import com.abbisea.caves.attributes.types.CombatItem
import com.abbisea.caves.attributes.types.Food
import com.abbisea.caves.attributes.types.iconTile
import com.abbisea.caves.extensions.GameItem
import com.abbisea.caves.extensions.whenTypeIs
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment

class InventoryRowFragment(width: Int, item: GameItem) : Fragment {

    val dropButton = Components.button()
        .withDecorations()
        .withText("Drop")
        .build()

    val eatButton = Components.button()
        .withDecorations()
        .withText("Eat")
        .build()

    val equipButton = Components.button()
        .withDecorations()
        .withText("Equip")
        .build()

    override val root = Components.hbox()
        .withSpacing(1)
        .withSize(width, 1)
        .build().apply {
            addComponent(
                Components.icon()
                    .withIcon(item.iconTile)
            )
            addComponent(
                Components.label()
                    .withSize(InventoryFragment.NAME_COLUMN_WIDTH, 1)
                    .withText(item.name)
            )
            addComponent(dropButton)
            item.whenTypeIs<Food> {
                addComponent(eatButton)
            }
            item.whenTypeIs<CombatItem> {
                addComponent(equipButton)
            }
        }
}