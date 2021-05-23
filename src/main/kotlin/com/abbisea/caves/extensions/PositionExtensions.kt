package com.abbisea.caves.extensions

import org.hexworks.zircon.api.data.Position3D

fun Position3D.sameLevelNeighboursShuffled(): List<Position3D> =
    (-1..1).flatMap { x ->
        (-1..1).map { y ->
            this.withRelativeX(x).withRelativeY(y)
        }
    }.minus(this).shuffled()
