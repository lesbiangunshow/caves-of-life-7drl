package com.caves.world

import com.caves.attributes.types.Player
import org.hexworks.amethyst.api.Context
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.UIEvent

data class GameContext(
    val world: String,
    val screen: Screen,
    val uiEvent: UIEvent,
    val player: Player
): Context