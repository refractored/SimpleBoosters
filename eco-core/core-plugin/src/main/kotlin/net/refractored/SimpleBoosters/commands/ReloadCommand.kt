package net.refractored.SimpleBoosters.commands

import com.willfp.eco.util.toComponent
import net.refractored.SimpleBoosters.SimpleBoostersPlugin
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.bukkit.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission

class ReloadCommand {
    @CommandPermission("joblistings.admin.reload")
    @Description("Reloads plugin configuration")
    @Command("simpleboosters reload")
    fun execute(actor: BukkitCommandActor) {
        SimpleBoostersPlugin.instance.reload()
        actor.reply(
            SimpleBoostersPlugin.instance.langYml
                .getMessage("command.reloaded")
                .toComponent(),
        )
    }
}
