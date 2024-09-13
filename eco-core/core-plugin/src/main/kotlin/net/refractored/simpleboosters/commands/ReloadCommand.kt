package net.refractored.simpleboosters.commands

import net.refractored.simpleboosters.SimpleBoostersPlugin
import net.refractored.simpleboosters.util.MessageUtil.miniToComponent
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.bukkit.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission

class ReloadCommand {
    @CommandPermission("simpleboosters.admin.reload")
    @Description("Reloads plugin configuration")
    @Command("simpleboosters reload")
    fun execute(actor: BukkitCommandActor) {
        SimpleBoostersPlugin.instance.reloadWithTime()
        actor.reply(
            SimpleBoostersPlugin.instance.langYml
                .getString("messages.reloaded")
                .miniToComponent(),
        )
    }
}
