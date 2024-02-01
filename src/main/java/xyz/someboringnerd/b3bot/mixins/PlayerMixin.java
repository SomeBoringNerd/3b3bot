package xyz.someboringnerd.b3bot.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.someboringnerd.b3bot.ClientInit;
import xyz.someboringnerd.b3bot.events.PlayerTickEvent;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin {

    @Shadow public abstract GameProfile getGameProfile();

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if(this.getGameProfile().getName().equalsIgnoreCase(MinecraftClient.getInstance().player.getGameProfile().getName())) {
            ClientInit.EVENT_BUS.post(PlayerTickEvent.get());
        }
    }

}
