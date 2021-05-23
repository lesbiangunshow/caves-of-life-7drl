package com.abbisea.caves

import com.abbisea.caves.view.StartView
import org.hexworks.zircon.api.SwingApplications

fun main(args: Array<String>) {
    val grid = SwingApplications.startTileGrid(GameConfig.buildAppConfig())
    StartView(grid).dock()
}
