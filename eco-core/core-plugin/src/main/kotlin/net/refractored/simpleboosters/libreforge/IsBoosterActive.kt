package net.refractored.simpleboosters.libreforge

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.libreforge.Dispatcher
import com.willfp.libreforge.NoCompileData
import com.willfp.libreforge.ProvidedHolder
import com.willfp.libreforge.arguments
import com.willfp.libreforge.conditions.Condition
import net.refractored.simpleboosters.booster.RegisteredBoosters

object IsBoosterActive : Condition<NoCompileData>("is_simplebooster_active") {
    override val arguments =
        arguments {
            require("booster", "Specify the booster!")
        }

    override fun isMet(
        dispatcher: Dispatcher<*>,
        config: Config,
        holder: ProvidedHolder,
        compileData: NoCompileData,
    ): Boolean = RegisteredBoosters.getBoosterById(config.getString("booster"))?.isActive() ?: false
}
