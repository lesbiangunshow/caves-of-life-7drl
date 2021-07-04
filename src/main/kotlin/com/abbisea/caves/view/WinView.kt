package com.abbisea.caves.view

import com.abbisea.caves.GameConfig
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView
import kotlin.system.exitProcess

class WinView(
    private val grid: TileGrid,
    zircons: Int
) : BaseView(grid, ColorThemes.arc()) {

    init {
        val header = Components.textBox(GameConfig.WINDOW_WIDTH / 2)
            .addHeader("You won!")
            .addNewLine()
            .addParagraph("Congratulations! You have escaped from Caves of Zircon!", withNewLine = false)
            .addParagraph("You've managed to find $zircons Zircons.")
            .withAlignmentWithin(screen, ComponentAlignment.CENTER)
            .build()

        val restartButton = Components.button()
            .withAlignmentAround(header, ComponentAlignment.BOTTOM_LEFT)
            .withText("Restart")
            .withDecorations(box())
            .build()

        val exitButton = Components.button()
            .withAlignmentAround(header, ComponentAlignment.BOTTOM_RIGHT)
            .withText("Quit")
            .withDecorations(box())
            .build()

        restartButton.onActivated {
            replaceWith(PlayView(grid))
        }

        exitButton.onActivated {
            exitProcess(0)
        }

        screen.addComponents(header, restartButton, exitButton)
    }
}