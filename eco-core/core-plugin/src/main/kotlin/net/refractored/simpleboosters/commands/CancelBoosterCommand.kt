package net.refractored.simpleboosters.commands

import net.refractored.simpleboosters.SimpleBoostersPlugin
import net.refractored.simpleboosters.booster.Booster
import net.refractored.simpleboosters.util.MessageUtil.getStringPrefixed
import net.refractored.simpleboosters.util.MessageUtil.miniToComponent
import net.refractored.simpleboosters.util.MessageUtil.replace
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.bukkit.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission

class CancelBoosterCommand {
    @CommandPermission("simpleboosters.admin.start")
    @Description("Starts a booster")
    @Command("simpleboosters start")
    fun execute(
        actor: BukkitCommandActor,
        booster: Booster,
    ) {
        if (booster.active != null) {
            actor.reply(
                SimpleBoostersPlugin.instance.langYml
                    .getStringPrefixed("messages.already-active")
                    .replace("%booster%", booster.name)
                    .miniToComponent(),
            )
            return
        }
        booster.activateBooster()
        actor.reply(
            SimpleBoostersPlugin.instance.langYml
                .getStringPrefixed("messages.started-booster")
                .replace("%booster%", booster.name)
                .miniToComponent(),
        )
    }
}
