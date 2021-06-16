package com.abbisea.caves.attributes.types

import com.abbisea.caves.attributes.NutritionalValue
import com.abbisea.caves.extensions.GameEntity
import com.abbisea.caves.extensions.tryToFindAttribute

interface Food : Item

val GameEntity<Food>.energy: Int
    get() = tryToFindAttribute(NutritionalValue::class).energy