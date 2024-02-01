package xyz.someboringnerd.b3bot.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void onScreenOpen(CallbackInfo ci) {
        if(MinecraftClient.getInstance().currentScreen instanceof DeathScreen) {
            MinecraftClient.getInstance().player.requestRespawn();
        }
    }

}
