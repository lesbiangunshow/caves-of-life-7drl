package com.abbisea.caves.functions

import com.abbisea.caves.events.GameLogEvent
import org.hexworks.zircon.internal.Zircon

fun logGameEvent(text: String, emitter: Any) {
    Zircon.eventBus.publish(GameLogEvent(text, emitter))
}