package net.refractored.simpleboosters

import com.willfp.libreforge.SimpleProvidedHolder
import com.willfp.libreforge.conditions.Conditions
import com.willfp.libreforge.loader.LibreforgePlugin
import com.willfp.libreforge.loader.configs.ConfigCategory
import com.willfp.libreforge.registerGenericHolderProvider
import net.refractored.simpleboosters.booster.RegisteredBoosters
import net.refractored.simpleboosters.commands.*
import net.refractored.simpleboosters.libreforge.IsBoosterActive
import revxrsal.commands.bukkit.BukkitCommandHandler

class SimpleBoostersPlugin : LibreforgePlugin() {
    lateinit var handler: BukkitCommandHandler

    override fun loadConfigCategories(): List<ConfigCategory> =
        listOf(
            RegisteredBoosters,
        )

    override fun handleEnable() {
        instance = this

        handler = BukkitCommandHandler.create(this)

        handler.register(ReloadCommand())

        handler.registerBrigadier()

        Conditions.register(IsBoosterActive)

        registerGenericHolderProvider {
            RegisteredBoosters.getActiveBoosters().map { SimpleProvidedHolder(it) }
        }
    }

    override fun handleDisable() {
        if (this::handler.isInitialized) {
            handler.unregisterAllCommands()
        }
    }

    companion object {
        lateinit var instance: SimpleBoostersPlugin
            private set
    }
}
