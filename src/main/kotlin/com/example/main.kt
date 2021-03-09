package com.example

import com.example.view.StartView
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.extensions.toScreen

fun main(args: Array<String>) {
    val grid = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                    .withSize(60, 30)
                    .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
                    .build())
    StartView(grid).dock()

//    val screen = tileGrid.toScreen()
//
//    screen.addComponent(Components.label()
//            .withText("Hello, Zircon!")
//            .withPosition(23, 10))
//
//    screen.display()
//    screen.theme = ColorThemes.arc()
}
