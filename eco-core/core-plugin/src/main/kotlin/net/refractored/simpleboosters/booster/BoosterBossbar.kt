package net.refractored.simpleboosters.booster

import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import java.time.Duration

class BoosterBossbar(val activeBooster: ActiveBooster) {

    fun getRemainingTime(): Duration = Duration.ofMillis(activeBooster.getRemainingMillis())

    fun getBossbarTitle(): String{
        val duration = getRemainingTime()
        return activeBooster.booster.config.getString("bossbar.title")
            .replace("%hours%", duration.toHours().toString())
            .replace("%minutes%", duration.toMinutesPart().toString())
            .replace("%seconds%", duration.toSecondsPart().toString())
    }

    var bossbar =
        Bukkit.createBossBar(
            getBossbarTitle(),
            BarColor.valueOf(activeBooster.booster.config.getString("bossbar.color").uppercase()),
            BarStyle.valueOf(activeBooster.booster.config.getString("bossbar.style").uppercase()),
        )

    init {
        Bukkit.getOnlinePlayers().forEach {
            bossbar.addPlayer(it)
        }
        bossbar.isVisible = true
    }

    fun updateBossbar(){
        val progress = (activeBooster.getRemainingMillis() / activeBooster.length.toDouble()).coerceIn(0.0, 1.0)
        bossbar.progress = progress
        bossbar.setTitle(getBossbarTitle())
    }

    fun deactivateBossbar(){
        bossbar.removeAll()
    }
}