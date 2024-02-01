package xyz.someboringnerd.b3bot.systems.commands.impl;

import xyz.someboringnerd.b3bot.systems.commands.AbstractCommand;
import xyz.someboringnerd.b3bot.systems.commands.Command;
import xyz.someboringnerd.b3bot.util.ChatUtil;
import xyz.someboringnerd.b3bot.util.DataBaseUtil;

import java.security.SecureRandom;

@Command(name = "quote")
public class QuoteCommand extends AbstractCommand {

    @Override
    protected void execute(String sender, String[] args) {

        String player = args.length > 1 ? args[1] : sender;
        if(args.length > 1 && args[1].equalsIgnoreCase("GayWitchMorgane")) {
            ChatUtil.sendMessage("Not quoting myself, it's just command output.");
        }
        else {
            String[] quoteList = DataBaseUtil.getMessageFromPlayer(player);
            if (quoteList.length == 0) {
                ChatUtil.sendMessage("I never saw that player.");
            } else {
                String quote = quoteList[new SecureRandom().nextInt(0, quoteList.length)];

                ChatUtil.sendMessage("\"%s\", - %s", quote, player);
            }
        }
    }

}
