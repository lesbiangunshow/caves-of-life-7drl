package com.abbisea.caves.view

import com.abbisea.caves.GameConfig.LOG_AREA_HEIGHT
import com.abbisea.caves.GameConfig.SIDEBAR_WIDTH
import com.abbisea.caves.GameConfig.THEME
import com.abbisea.caves.GameConfig.WINDOW_HEIGHT
import com.abbisea.caves.GameConfig.WINDOW_WIDTH
import com.abbisea.caves.builders.GameBuilder
import com.abbisea.caves.builders.GameTileRepository
import com.abbisea.caves.events.GameLogEvent
import com.abbisea.caves.world.Game
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.KeepSubscription
import org.hexworks.cobalt.events.api.subscribeTo
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.view.base.BaseView
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.game.impl.GameAreaComponentRenderer

class PlayView(
    private val grid: TileGrid,
    private val game: Game = GameBuilder.create(),
    theme: ColorTheme = THEME
) : BaseView(grid, theme) {
    init {
        val sidebar = Components.panel()
            .withSize(
                SIDEBAR_WIDTH, WINDOW_HEIGHT)
            .withDecorations(box())
            .build()

        val logArea = Components.logArea()
            .withDecorations(box(title = "Log"))
            .withSize(WINDOW_WIDTH - SIDEBAR_WIDTH, LOG_AREA_HEIGHT)
            .withAlignmentWithin(screen, ComponentAlignment.BOTTOM_RIGHT)
            .build()

        val gameComponent = Components.panel()
            .withSize(game.world.visibleSize.to2DSize())
            .withComponentRenderer(
                GameAreaComponentRenderer(
                    gameArea = game.world,
                    projectionMode = ProjectionMode.TOP_DOWN.toProperty(),
                    fillerTile = GameTileRepository.FLOOR
                )
            )
            .withAlignmentWithin(screen, ComponentAlignment.TOP_RIGHT)
            .build()

        screen.addComponents(sidebar, logArea, gameComponent)

        screen.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { event, _ ->
            game.world.update(screen, event, game)
            Processed
        }

        Zircon.eventBus.subscribeTo<GameLogEvent> { (text) ->
            logArea.addParagraph(
                paragraph = text,
                withNewLine = false,
                withTypingEffectSpeedInMs = 50
            )
            KeepSubscription
        }
    }
}