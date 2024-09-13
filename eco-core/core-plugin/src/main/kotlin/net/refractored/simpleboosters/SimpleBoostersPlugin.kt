package net.refractored.simpleboosters

import com.willfp.libreforge.SimpleProvidedHolder
import com.willfp.libreforge.conditions.Conditions
import com.willfp.libreforge.loader.LibreforgePlugin
import com.willfp.libreforge.loader.configs.ConfigCategory
import com.willfp.libreforge.registerGenericHolderProvider
import net.refractored.simpleboosters.autocompletion.BoosterResolver
import net.refractored.simpleboosters.booster.Booster
import net.refractored.simpleboosters.booster.RegisteredBoosters
import net.refractored.simpleboosters.commands.*
import net.refractored.simpleboosters.libreforge.IsBoosterActive
import org.bukkit.Bukkit
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

        val boosterResolver = BoosterResolver()

        handler.autoCompleter.registerParameterSuggestions(Booster::class.java, boosterResolver)

        handler.registerValueResolver(Booster::class.java, boosterResolver)

        handler.register(ReloadCommand())
        handler.register(StartBoosterCommand())
        handler.register(BoosterInfoCommand())

        handler.registerBrigadier()

        Conditions.register(IsBoosterActive)

        registerGenericHolderProvider {
            RegisteredBoosters.getActiveBoosters().map { SimpleProvidedHolder(it) }
        }

        Bukkit.getScheduler().runTaskTimer(
            this,
            Runnable {
                for (booster in RegisteredBoosters.getActiveBoosters()) {
                    val activeBooster = booster.active ?: continue
                    if (activeBooster.isExpired()) {
                        booster.deactivateBooster()
                    }
                }
            },
            0,
            20,
        )
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
