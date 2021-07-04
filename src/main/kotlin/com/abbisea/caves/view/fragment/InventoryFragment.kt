package com.abbisea.caves.view.fragment

import com.abbisea.caves.attributes.Inventory
import com.abbisea.caves.extensions.GameItem
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.component.VBox

class InventoryFragment(
    inventory: Inventory,
    width: Int,
    private val onDrop: (GameItem) -> Unit,
    private val onEat: (GameItem) -> Unit,
    private val onEquip: (GameItem) -> Maybe<GameItem>,
    private val onExamine: (GameItem) -> Unit
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
                addRow(width, item)
            }
        }

    private fun VBox.addRow(width: Int, item: GameItem) {
        val row = InventoryRowFragment(width, item)
        addFragment(row).apply {
            row.dropButton.onActivated {
                detach()
                onDrop(item)
            }
            row.examineButton.onActivated {
                onExamine(item)
            }
            row.eatButton.onActivated {
                detach()
                onEat(item)
            }
            row.equipButton.onActivated {
                onEquip(item).map { oldItem ->
                    detach()
                    addRow(width, oldItem)
                }
            }
        }
    }
}