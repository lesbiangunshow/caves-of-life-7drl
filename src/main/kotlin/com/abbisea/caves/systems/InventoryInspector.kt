package com.abbisea.caves.systems

import com.abbisea.caves.GameConfig
import com.abbisea.caves.attributes.types.EnergyUser
import com.abbisea.caves.attributes.types.Food
import com.abbisea.caves.attributes.types.inventory
import com.abbisea.caves.extensions.whenTypeIs
import com.abbisea.caves.messages.DropItem
import com.abbisea.caves.messages.Eat
import com.abbisea.caves.messages.InspectInventory
import com.abbisea.caves.view.fragment.InventoryFragment
import com.abbisea.caves.world.GameContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.platform.Dispatchers
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

object InventoryInspector : BaseFacet<GameContext, InspectInventory>(InspectInventory::class) {

    val DIALOG_SIZE = Size.create(35, 15)

    override suspend fun receive(message: InspectInventory): Response {
        val (context, itemHolder, position) = message

        val screen = context.screen

        val panel = Components.panel()
            .withSize(DIALOG_SIZE)
            .withDecorations(box(title = "Inventory"), shadow())
            .build()

        val fragment = InventoryFragment(
            inventory = itemHolder.inventory,
            width = DIALOG_SIZE.width - 3,
            onDrop = { item ->
                CoroutineScope(Dispatchers.Single).launch {
                    itemHolder.receiveMessage(DropItem(context, itemHolder, item, position))
                }
            },
            onEat = { item ->
                CoroutineScope(Dispatchers.Single).launch {
                    itemHolder.whenTypeIs<EnergyUser> { eater ->
                        item.whenTypeIs<Food> { food ->
                            itemHolder.inventory.removeItem(food)
                            itemHolder.receiveMessage(Eat(context, eater, food))
                        }
                    }
                }
            }

        )

        panel.addFragment(fragment)

        val modal = ModalBuilder.newBuilder<EmptyModalResult>()
            .withParentSize(screen.size)
            .withComponent(panel)
            .withCenteredDialog(true)
            .build()

        panel.addComponent(
            Components.button()
                .withText("Close")
                .withAlignmentWithin(panel, ComponentAlignment.BOTTOM_LEFT)
                .build().apply {
                    onActivated {
                        modal.close(EmptyModalResult)
                    }
                }
        )

        modal.theme = GameConfig.THEME
        screen.openModal(modal)
        return Consumed
    }
}