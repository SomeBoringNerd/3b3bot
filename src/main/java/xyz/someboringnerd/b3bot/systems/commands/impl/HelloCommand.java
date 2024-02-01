package xyz.someboringnerd.b3bot.systems.commands.impl;

import net.minecraft.client.MinecraftClient;
import xyz.someboringnerd.b3bot.systems.commands.Command;
import xyz.someboringnerd.b3bot.systems.commands.AbstractCommand;
import xyz.someboringnerd.b3bot.util.ChatUtil;

@Command(name = "hello")
public class HelloCommand extends AbstractCommand {

    @Override
    public void execute(String sender, String[] args) {
        ChatUtil.sendMessage("Hello %s, my name is %s", sender, MinecraftClient.getInstance().player.getGameProfile().getName());
    }

}
