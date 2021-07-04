package com.abbisea.caves.systems

import com.abbisea.caves.attributes.CombatStats
import com.abbisea.caves.attributes.types.ExperienceGainer
import com.abbisea.caves.attributes.types.combatStats
import com.abbisea.caves.attributes.types.experience
import com.abbisea.caves.events.PlayerGainedLevel
import com.abbisea.caves.extensions.attackValue
import com.abbisea.caves.extensions.defenseValue
import com.abbisea.caves.extensions.isPlayer
import com.abbisea.caves.extensions.whenTypeIs
import com.abbisea.caves.functions.logGameEvent
import com.abbisea.caves.messages.EntityDestroyed
import com.abbisea.caves.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.zircon.internal.Zircon
import kotlin.math.min

object ExperienceAccumulator : BaseFacet<GameContext, EntityDestroyed>(EntityDestroyed::class) {

    override suspend fun receive(message: EntityDestroyed): Response {
        val (_, defender, attacker) = message
        attacker.whenTypeIs<ExperienceGainer> { experienceGainer ->
            val xp = experienceGainer.experience
            val stats = experienceGainer.combatStats
            val defenderHp = defender.findAttribute(CombatStats::class).map { it.maxHp }.orElse(0)
            val amount = (defenderHp + defender.attackValue + defender.defenseValue) - xp.currentLevel * 2
            if (amount > 0) {
                xp.currentXP += amount
                while (xp.currentXP > Math.pow(xp.currentLevel.toDouble(), 1.5) * 20) {
                    xp.currentLevel++
                    logGameEvent("$attacker advanced to level ${xp.currentLevel}.", ExperienceAccumulator)
                    stats.hpProperty.value = min(stats.hp + xp.currentLevel * 2, stats.maxHp)
                    if (attacker.isPlayer) {
                        Zircon.eventBus.publish(PlayerGainedLevel(ExperienceAccumulator))
                    }
                }
            }
        }
        return Consumed
    }
}