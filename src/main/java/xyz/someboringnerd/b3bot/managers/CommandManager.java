package xyz.someboringnerd.b3bot.managers;

import lombok.AccessLevel;
import lombok.Getter;
import org.reflections.Reflections;
import xyz.someboringnerd.b3bot.systems.commands.Command;
import xyz.someboringnerd.b3bot.systems.commands.AbstractCommand;
import xyz.someboringnerd.b3bot.systems.commands.PRIVACY;
import xyz.someboringnerd.b3bot.util.LogUtil;

import java.util.HashSet;
import java.util.Set;

public class CommandManager {

    @Getter(AccessLevel.PUBLIC)
    private final Set<AbstractCommand> registeredCommands = new HashSet<>();

    @Getter(AccessLevel.PUBLIC)
    private static CommandManager instance;

    public CommandManager() {

        instance = this;

        new Reflections("xyz.someboringnerd.b3bot.systems.commands.impl").getSubTypesOf(AbstractCommand.class).forEach(command -> {
            if(command.getAnnotation(Command.class) == null) {
                throw new RuntimeException("Command " + command.getSimpleName() + " dont have a Command annotation, fix asap");
            }

            try {
                registeredCommands.add(command.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        });
    }

    public void execute(String sender, String command) {

        String commandName = command.split(" ")[0].substring(1);    // remove prefix

        registeredCommands.forEach(commandInstance -> {
            if(commandInstance.getClass().getAnnotation(Command.class).name().equalsIgnoreCase(commandName)) {

                if(commandInstance.getClass().getAnnotation(Command.class).privacy() == PRIVACY.PUBLIC || isValidSender(sender))
                    commandInstance.preExecute(sender, command.substring(command.split(" ")[0].length()).split(" "));

            }
        });
    }

    private boolean isValidSender(String sender) {
        return sender.equalsIgnoreCase("someboringnerd");
    }

}
