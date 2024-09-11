package net.refractored.SimpleBoosters.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

object MessageUtil {
    fun Component.toMinimessage(): String = MiniMessage.miniMessage().serialize(this)

    /**
     * Returns a new string obtained by replacing all occurrences of the [oldValue] substring in this string
     * with the specified [newValue] component formatted as minimessage.
     *
     */
    fun String.replace(
        oldValue: String,
        newValue: Component,
        ignoreCase: Boolean = false,
    ) {
        this.replace(oldValue, newValue.toMinimessage(), ignoreCase)
    }
}
