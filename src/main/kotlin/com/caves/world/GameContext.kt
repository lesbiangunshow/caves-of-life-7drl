package com.caves.world

import org.hexworks.amethyst.api.Context
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.UIEvent

data class GameContext(
    val world: String,
    val screen: Screen,
    val uiEvent: UIEvent,
    val player: String
): Context