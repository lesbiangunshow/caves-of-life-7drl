package com.abbisea.caves.view.fragment

import com.abbisea.caves.attributes.Inventory
import com.abbisea.caves.extensions.GameItem
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment

class InventoryFragment(
    inventory: Inventory,
    width: Int,
    onDrop: (GameItem) -> Unit,
    onEat: (GameItem) -> Unit
) : Fragment {

    companion object {
        const val NAME_COLUMN_WIDTH = 15
        const val ACTIONS_COLUMN_WIDTH = 10
    }

    override val root = Components.vbox()
        .withSize(width, inventory.size + 1)
        .build().apply {
            addComponent(
                Components.hbox()
                    .withSpacing(1)
                    .withSize(width, 1)
                    .build().apply {
                        addComponent(Components.label().withText("").withSize(1, 1))
                        addComponent(Components.header().withText("Name").withSize(NAME_COLUMN_WIDTH, 1))
                        addComponent(Components.header().withText("Actions").withSize(ACTIONS_COLUMN_WIDTH, 1))
                    }
            )
            inventory.items.forEach { item ->
                val row = InventoryRowFragment(width, item)
                addFragment(row).apply {
                    row.dropButton.onActivated {
                        detach()
                        onDrop(item)
                    }
                    row.eatButton.onActivated {
                        detach()
                        onEat(item)
                    }
                }
            }
        }
}