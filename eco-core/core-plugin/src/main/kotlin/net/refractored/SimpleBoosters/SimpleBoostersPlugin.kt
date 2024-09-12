package net.refractored.SimpleBoosters

import com.willfp.libreforge.loader.LibreforgePlugin
import net.refractored.SimpleBoosters.commands.*
import revxrsal.commands.bukkit.BukkitCommandHandler

class SimpleBoostersPlugin : LibreforgePlugin() {
    lateinit var handler: BukkitCommandHandler

    override fun handleEnable() {
        instance = this

        handler = BukkitCommandHandler.create(this)

        handler.register(ReloadCommand())

        handler.registerBrigadier()

    }

    override fun handleDisable() {
        if (this::handler.isInitialized) {
            handler.unregisterAllCommands()
        }
    }

    companion object {
        lateinit var instance: SimpleBoostersPlugin
    }
}
