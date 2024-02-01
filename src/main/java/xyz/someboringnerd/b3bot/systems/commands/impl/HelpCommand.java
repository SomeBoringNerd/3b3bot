package xyz.someboringnerd.b3bot.systems.commands.impl;

import xyz.someboringnerd.b3bot.managers.CommandManager;
import xyz.someboringnerd.b3bot.systems.commands.AbstractCommand;
import xyz.someboringnerd.b3bot.systems.commands.Command;
import xyz.someboringnerd.b3bot.systems.commands.PRIVACY;
import xyz.someboringnerd.b3bot.util.ChatUtil;

import java.util.concurrent.atomic.AtomicReference;

@Command(name = "help")
public class HelpCommand extends AbstractCommand {
    @Override
    protected void execute(String sender, String[] args) {

        AtomicReference<String> cmd = new AtomicReference<>("");
        CommandManager.getInstance().getRegisteredCommands().forEach(command -> {
            if(command.getClass().getAnnotation(Command.class).privacy() != PRIVACY.DEV_ONLY)
                cmd.set(cmd.get() + command.getClass().getAnnotation(Command.class).name() + ", ");
        });

        cmd.set(cmd.get().substring(0, cmd.get().length() - 2));

        ChatUtil.sendMessage("Prefix : >, list of available commands : " + cmd.get());
    }
}
