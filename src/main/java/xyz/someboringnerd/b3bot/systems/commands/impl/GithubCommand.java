package xyz.someboringnerd.b3bot.systems.commands.impl;

import xyz.someboringnerd.b3bot.systems.commands.AbstractCommand;
import xyz.someboringnerd.b3bot.systems.commands.Command;
import xyz.someboringnerd.b3bot.util.ChatUtil;

@Command(name = "github")
public class GithubCommand extends AbstractCommand {
    @Override
    protected void execute(String sender, String[] args) {
        ChatUtil.sendMessage("Please star the repo : https://github.com/someboringnerd/3b3bot");
    }
}
