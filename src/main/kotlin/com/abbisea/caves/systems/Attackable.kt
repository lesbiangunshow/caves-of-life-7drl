package com.abbisea.caves.systems

import com.abbisea.caves.attributes.types.combatStats
import com.abbisea.caves.extensions.hasNoHealthLeft
import com.abbisea.caves.extensions.isPlayer
import com.abbisea.caves.functions.logGameEvent
import com.abbisea.caves.messages.Attack
import com.abbisea.caves.messages.Destroy
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import java.lang.Integer.max

object Attackable: BaseFacet<GameContext, Attack>(Attack::class) {
    override suspend fun receive(message: Attack): Response {
        val (context, attacker, target) = message
        return if (attacker.isPlayer || target.isPlayer) {
            val damage = max(0, attacker.combatStats.attackValue - target.combatStats.defenseValue)
            val finalDamage = (Math.random() * damage).toInt() + 1
            target.combatStats.hp -= finalDamage
            logGameEvent("The $attacker hits the $target for $finalDamage!", Attackable)
            if (target.hasNoHealthLeft()) {
                target.sendMessage(
                    Destroy(
                        context = context,
                        source = attacker,
                        target = target,
                        cause = "a blow to the head."
                    )
                )
            }
            Consumed
        } else Pass
    }
}