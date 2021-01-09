package sep.mixin;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sep.features.StatusEffectHUDFeature;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "render",at = @At("HEAD"))
    private void onRender(MatrixStack matrixStack, float f, CallbackInfo cbi) {
            if(!(client.currentScreen instanceof AbstractInventoryScreen)){
                    if(client.player != null){
                        new StatusEffectHUDFeature().render(matrixStack, client.player.getStatusEffects());
                }
            }
    }

    @Inject(method = "renderStatusEffectOverlay",at = @At("HEAD"), cancellable =  true)
    private void onRenderStatusEffectOverlay(CallbackInfo c){
        c.cancel();
    }

}
