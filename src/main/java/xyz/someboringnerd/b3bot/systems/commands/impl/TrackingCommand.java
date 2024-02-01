package xyz.someboringnerd.b3bot.systems.commands.impl;

import xyz.someboringnerd.b3bot.managers.SessionManager;
import xyz.someboringnerd.b3bot.systems.commands.AbstractCommand;
import xyz.someboringnerd.b3bot.systems.commands.Command;
import xyz.someboringnerd.b3bot.systems.commands.PRIVACY;
import xyz.someboringnerd.b3bot.util.ChatUtil;

@Command(name = "tracked", privacy = PRIVACY.DEV_ONLY)
public class TrackingCommand extends AbstractCommand {
    @Override
    protected void execute(String sender, String[] args) {
        if(sender.equalsIgnoreCase("SomeBoringNerd")) {
            if(args[1].equalsIgnoreCase("add-all")) {
                SessionManager.getInstance().addAll();
            } else if(args[1].equalsIgnoreCase("save-all")) {
                SessionManager.getInstance().saveAll();
            }
            else {
                ChatUtil.sendMessage("I'm tracking %s sessions right now", SessionManager.getInstance().getTrackedSessionNumber());
            }
        }
    }
}
