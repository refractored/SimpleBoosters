package net.refractored.simpleboosters.listeners

import net.refractored.simpleboosters.booster.RegisteredBoosters
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent


class OnPlayerJoin: Listener {
    @EventHandler(priority = EventPriority.LOW)
    fun onJoin(event: PlayerJoinEvent) {
        for (activeBooster in RegisteredBoosters.getActiveBoosters()) {
            activeBooster.active?.bossbar?.bossbar?.addPlayer(event.player) ?: return
        }
    }
}