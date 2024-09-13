package net.refractored.simpleboosters.commands

import net.refractored.simpleboosters.SimpleBoostersPlugin
import net.refractored.simpleboosters.booster.Booster
import net.refractored.simpleboosters.exceptions.CommandErrorException
import net.refractored.simpleboosters.util.MessageUtil.miniToComponent
import net.refractored.simpleboosters.util.MessageUtil.replace
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.bukkit.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission

class BoosterInfoCommand {
    @CommandPermission("simpleboosters.admin.info")
    @Description("Starts a booster")
    @Command("simpleboosters info")
    fun execute(
        actor: BukkitCommandActor,
        booster: Booster,
    ) {
        val activeBooster =
            booster.active ?: throw CommandErrorException(
                SimpleBoostersPlugin.instance.langYml
                    .getString(
                        "messages.info-booster-inactive",
                    ).replace("%booster%", booster.name)
                    .miniToComponent(),
            )
        val activeBoosterDuration = activeBooster.getRemainingDuration()
        actor.reply(
            SimpleBoostersPlugin.instance.langYml
                .getString("messages.info-booster")
                .replace("%booster%", booster.name)
                .replace(
                    "%time%",
                    "${activeBoosterDuration.toHoursPart()}:${activeBoosterDuration.toMinutesPart()}:${activeBoosterDuration.toSecondsPart()}",
                ).miniToComponent(),
        )
    }
}
