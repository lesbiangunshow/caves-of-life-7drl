package com.abbisea.caves.attributes

import com.abbisea.caves.GameConfig
import org.hexworks.amethyst.api.base.BaseAttribute

data class FungusSpread(
    var spreadCount: Int = 0,
    val maximumSpread: Int = GameConfig.MAXIMUM_FUNGUS_SPREAD
): BaseAttribute()