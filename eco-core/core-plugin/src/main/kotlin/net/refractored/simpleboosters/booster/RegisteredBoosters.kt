package net.refractored.simpleboosters.booster

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.libreforge.loader.LibreforgePlugin
import com.willfp.libreforge.loader.configs.ConfigCategory
import java.time.Duration
import java.util.*

object RegisteredBoosters : ConfigCategory("booster", "boosters") {
    /**
     * List of registered boosters.
     */
    @JvmStatic
    private val registeredBoosters = mutableListOf<Booster>()

    @JvmStatic
    fun getBooster(booster: Booster): Booster? = registeredBoosters.firstOrNull { it == booster }

    @JvmStatic
    fun getBoosters() = registeredBoosters.toList()

    @JvmStatic
    fun unregisterAllBoosters() {
        registeredBoosters.clear()
    }

    @JvmStatic
    fun registerBooster(
        booster: Booster,
        time: Long,
    ) {
        if (getBooster(booster) != null) throw IllegalArgumentException("Booster already registered")
        registeredBoosters.add(booster)
    }

    @JvmStatic
    fun unregisterBooster(booster: Booster) {
        registeredBoosters.remove(booster)
    }

    @JvmStatic
    fun getBoosterById(id: String): Booster? = registeredBoosters.firstOrNull { it.id.key == id }

    @JvmStatic
    fun getActiveBoosters() = registeredBoosters.filter { it.isActive() }

    @JvmStatic
    fun updateStatus() {
        for (booster in registeredBoosters) {
            val activatedBooster = booster.active ?: continue
            if (!activatedBooster.isExpired()) continue
            booster.deactivateBooster()
        }
    }

    override fun clear(plugin: LibreforgePlugin) {
        registeredBoosters.clear()
    }

    override fun acceptConfig(
        plugin: LibreforgePlugin,
        id: String,
        config: Config,
    ) {
        val booster = Booster(id, config)
        registeredBoosters.add(booster)
        if (booster.SavedExpireTime != 0.0) {
            booster.activateBooster(Duration.ofMillis(booster.SavedExpireTime.toLong()))
        }
    }
}
