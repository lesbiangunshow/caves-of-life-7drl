package com.abbisea.caves.attributes.types

import com.abbisea.caves.attributes.ZirconCounter
import com.abbisea.caves.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

interface ZirconHolder : EntityType

val GameEntity<ZirconHolder>.zirconCounter: ZirconCounter
    get() = findAttribute(ZirconCounter::class).get()