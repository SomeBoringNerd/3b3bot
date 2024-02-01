package xyz.someboringnerd.b3bot.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

@UtilityClass
public class ChatUtil {

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private static boolean ignore = false;

    public static void sendMessage(String message, Object... parsing) {
        message = String.format(message, parsing);

        MinecraftClient.getInstance().getNetworkHandler().sendChatMessage(message + " | WaifuHax bot-mode");
    }

}
