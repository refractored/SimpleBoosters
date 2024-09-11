package net.refractored.SimpleBoosters

import com.willfp.libreforge.loader.LibreforgePlugin
import revxrsal.commands.bukkit.BukkitCommandHandler

class SimpleBoostersPlugin : LibreforgePlugin() {
    lateinit var handler: BukkitCommandHandler

    override fun handleEnable() {
        handler = BukkitCommandHandler.create(this)

        handler.registerBrigadier()

        instance = this
    }

    override fun handleDisable() {
    }

    companion object {
        lateinit var instance: SimpleBoostersPlugin
    }
}
