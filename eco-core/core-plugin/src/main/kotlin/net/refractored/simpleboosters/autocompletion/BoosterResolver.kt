package net.refractored.simpleboosters.autocompletion

import net.refractored.simpleboosters.SimpleBoostersPlugin
import net.refractored.simpleboosters.booster.Booster
import net.refractored.simpleboosters.booster.RegisteredBoosters
import net.refractored.simpleboosters.exceptions.CommandErrorException
import net.refractored.simpleboosters.util.MessageUtil.miniToComponent
import revxrsal.commands.autocomplete.SuggestionProvider
import revxrsal.commands.command.CommandActor
import revxrsal.commands.command.ExecutableCommand
import revxrsal.commands.process.ValueResolver

class BoosterResolver :
    ValueResolver<Booster>,
    SuggestionProvider {
    override fun resolve(context: ValueResolver.ValueResolverContext): Booster {
        val boosterTag = context.arguments().pop()
        return RegisteredBoosters.getBoosterById(boosterTag) ?: run {
            throw CommandErrorException(
                SimpleBoostersPlugin.instance.langYml
                    .getString(
                        "messages.invalid-booster",
                    ).replace("%arg%", boosterTag)
                    .miniToComponent(),
            )
        }
    }

    override fun getSuggestions(
        args: MutableList<String>,
        sender: CommandActor,
        command: ExecutableCommand,
    ): MutableCollection<String> = RegisteredBoosters.getBoosters().map { it.id.key }.toMutableList()
}
