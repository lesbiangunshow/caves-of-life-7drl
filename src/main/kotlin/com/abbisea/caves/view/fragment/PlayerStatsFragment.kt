package com.abbisea.caves.view.fragment

import com.abbisea.caves.attributes.*
import com.abbisea.caves.attributes.types.Player
import com.abbisea.caves.extensions.GameEntity
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Fragment

class PlayerStatsFragment(
    width: Int,
    player: GameEntity<Player>
) : Fragment {

    override val root: Component = Components.vbox()
        .withSize(width, 30)
        .withSpacing(1)
        .build().apply {
            addComponent(Components.header().withText("Player"))

            player.attributes.toList().apply {
                findAndMap<Experience> {
                    addComponent(it.toComponent(width))
                }
                findAndMap<CombatStats> {
                    addComponent(it.toComponent(width))
                }
                findAndMap<EnergyLevel> {
                    addComponent(it.toComponent(width))
                }
                findAndMap<Equipment> {
                    addComponent(it.toComponent(width))
                }
            }
        }

    private inline fun <reified T : DisplayableAttribute> Iterable<Any>.findAndMap(fn: (T) -> Unit) {
        this.find { it is T }?.let { fn(it as T) }
    }
}