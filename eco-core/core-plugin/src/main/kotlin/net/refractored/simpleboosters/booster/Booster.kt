package net.refractored.simpleboosters.booster

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.libreforge.Holder
import com.willfp.libreforge.ViolationContext
import com.willfp.libreforge.conditions.Conditions
import com.willfp.libreforge.effects.Effects
import net.refractored.simpleboosters.SimpleBoostersPlugin
import org.bukkit.NamespacedKey
import java.time.Duration
import java.util.*

/**
 * Represents a booster.
 * @param stringID The string ID of the booster.
 * @param config The config of the booster.
 * @property active The active booster.
 */
class Booster(
    stringID: String,
    config: Config,
) : Holder {
    var active: ActiveBooster? = null
        private set

    override val conditions =
        Conditions.compile(
            config.getSubsections("conditions"),
            ViolationContext(SimpleBoostersPlugin.instance, "Booster $stringID"),
        )

    val name = config.getFormattedString("name")

    val duration = Duration.ofSeconds(config.getInt("duration").toLong())

    override val effects =
        Effects.compile(
            config.getSubsections("effects"),
            ViolationContext(SimpleBoostersPlugin.instance, "Booster $stringID"),
        )

    override val id: NamespacedKey = SimpleBoostersPlugin.instance.createNamespacedKey(stringID)

    /**
     * Activate the booster.
     * @param time The duration of how long the booster should be. If not specified, it will use the default time.
     */
    fun activateBooster(time: Duration = duration) {
        active = ActiveBooster(this, (System.currentTimeMillis() + time.toMillis()), UUID.randomUUID())
    }

    fun deactivateBooster() {
        active ?: throw IllegalStateException("Booster is not active.")
    }

    fun isActive() = active != null
}
