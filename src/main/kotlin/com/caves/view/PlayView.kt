package com.caves.view

import com.caves.GameConfig.LOG_AREA_HEIGHT
import com.caves.GameConfig.SIDEBAR_WIDTH
import com.caves.GameConfig.THEME
import com.caves.GameConfig.WINDOW_HEIGHT
import com.caves.GameConfig.WINDOW_WIDTH
import com.caves.builders.GameTileRepository
import com.caves.world.Game
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView
import org.hexworks.zircon.internal.game.impl.GameAreaComponentRenderer
import kotlin.math.log

class PlayView(
    private val grid: TileGrid,
    private val game: Game = Game.create(),
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

    }
}