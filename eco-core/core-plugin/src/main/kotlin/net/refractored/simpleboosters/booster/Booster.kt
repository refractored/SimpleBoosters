package net.refractored.simpleboosters.booster

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.eco.core.data.keys.PersistentDataKey
import com.willfp.eco.core.data.keys.PersistentDataKeyType
import com.willfp.eco.core.data.profile
import com.willfp.libreforge.Holder
import com.willfp.libreforge.ViolationContext
import com.willfp.libreforge.conditions.Conditions
import com.willfp.libreforge.effects.Effects
import net.refractored.simpleboosters.SimpleBoostersPlugin
import net.refractored.simpleboosters.util.MessageUtil.miniToComponent
import net.refractored.simpleboosters.util.MessageUtil.replace
import org.bukkit.Bukkit
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
    var stringID: String,
    var config: Config,
) : Holder {
    override val id: NamespacedKey = SimpleBoostersPlugin.instance.createNamespacedKey(stringID)

    val expiryTimeKey =
        PersistentDataKey(
            SimpleBoostersPlugin.instance.namespacedKeyFactory.create("${id.key}_expiry_time"),
            PersistentDataKeyType.DOUBLE,
            0.0,
        )

    var SavedExpireTime: Double
        get() = Bukkit.getServer().profile.read(expiryTimeKey)
        set(value) = Bukkit.getServer().profile.write(expiryTimeKey, value)

    var active: ActiveBooster? = null
        private set

    override val conditions =
        Conditions.compile(
            config.getSubsections("conditions"),
            ViolationContext(SimpleBoostersPlugin.instance, "Booster $stringID"),
        )

    val name = config.getString("name").miniToComponent()

    /**
     * The duration of the booster, from the config.
     * This is used when the booster is activated without a time.
     * @see activateBooster
     */
    val duration: Duration = Duration.ofSeconds(config.getInt("duration").toLong())

    override val effects =
        Effects.compile(
            config.getSubsections("effects"),
            ViolationContext(SimpleBoostersPlugin.instance, "Booster $stringID"),
        )

    /**
     * Activate the booster.
     * @param time The duration of how long the booster should be. If not specified, it will use the default time.
     */
    fun activateBooster(time: Duration = duration) {
        Bukkit.broadcast(
            config.getString("messages.activation").replace("%booster_name%", name).miniToComponent(),
        )
        for (commandString in config.getStrings("commands.activate")) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), commandString)
        }
        active = ActiveBooster(this, (time.toMillis()), UUID.randomUUID())
    }

    fun deactivateBooster() {
        active ?: throw IllegalStateException("Booster is not active.")
        Bukkit.broadcast(
            config.getString("messages.deactivation").replace("%booster_name%", name).miniToComponent(),
        )
        for (commandString in config.getStrings("commands.deactivate")) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), commandString)
        }
        SavedExpireTime = 0.0
        active = null
    }

    fun isActive() = active != null
}
