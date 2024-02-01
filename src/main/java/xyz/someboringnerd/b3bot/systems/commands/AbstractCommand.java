package xyz.someboringnerd.b3bot.systems.commands;

import lombok.extern.log4j.Log4j2;
import xyz.someboringnerd.b3bot.util.LogUtil;

public abstract class AbstractCommand {

    public AbstractCommand() {
        LogUtil.print("Initialized command %s", getClass().getSimpleName());
    }

    public void preExecute(String sender, String[] args) {
        execute(sender, args);
    }

    protected abstract void execute(String sender, String[] args);
}
