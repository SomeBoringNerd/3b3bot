package xyz.someboringnerd.b3bot.mixins;

import lombok.extern.log4j.Log4j2;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.someboringnerd.b3bot.ClientInit;
import xyz.someboringnerd.b3bot.events.PlayerJoinServerEvent;
import xyz.someboringnerd.b3bot.events.PlayerLeaveServerEvent;
import xyz.someboringnerd.b3bot.managers.CommandManager;
import xyz.someboringnerd.b3bot.util.DataBaseUtil;
import xyz.someboringnerd.b3bot.util.LogUtil;

@Mixin(ChatHud.class)
public class ChatHudMixin {

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At("TAIL"))
    private void onMessage(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh, CallbackInfo ci) {

        // determine if the message is a chat or another type of message

        // message is a chat message
        if(message.getString().startsWith("<")) {

            String sender = message.getString().split(" ")[0].replace("<", "").replace(">", "");
            String content = message.getString().substring(message.getString().split(" ")[0].length()).trim();

            if(message.getString().split(" ")[1].trim().startsWith(">")) {
                CommandManager.getInstance().execute(sender, content);
            }

            DataBaseUtil.addMessage(sender, content);
        }
        // login message
        else if (message.getString().split(" ")[1].equalsIgnoreCase("joined")) {
            LogUtil.print("Someone logged in, firing the event");
            ClientInit.EVENT_BUS.post(PlayerJoinServerEvent.get(message.getString().split(" ")[0].replace("<", "").replace(">", "")));
        }
        // disconnect
        else if (message.getString().split(" ")[1].equalsIgnoreCase("left")) {
            ClientInit.EVENT_BUS.post(PlayerLeaveServerEvent.get(message.getString().split(" ")[0].replace("<", "").replace(">", "")));
        }
    }


}
