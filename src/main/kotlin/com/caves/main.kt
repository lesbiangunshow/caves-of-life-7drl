package com.caves

import com.example.view.StartView
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.extensions.toScreen

fun main(args: Array<String>) {
    val grid = SwingApplications.startTileGrid(GameConfig.buildAppConfig())
    StartView(grid).dock()
}
