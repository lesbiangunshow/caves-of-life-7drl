package com.abbisea.caves.extensions

import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property

fun Property<Int>.toStringProperty(): Property<String> {
    return createPropertyFrom("").also { stringProp ->
        stringProp.updateFrom(this) {
            it.toString()
        }
    }
}