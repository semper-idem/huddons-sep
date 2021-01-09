package sep.mixin;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sep.config.SEPConfig;
import sep.features.StatusEffectHUDFeature;

@Mixin(AbstractInventoryScreen.class)
public class AbstractInventoryScreenMixin<T extends ScreenHandler> extends HandledScreen<T> {
    private StatusEffectHUDFeature statusEffectHUDFeature = new StatusEffectHUDFeature();

    @Shadow
    protected
    boolean drawStatusEffects;

    public AbstractInventoryScreenMixin(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "applyStatusEffectOffset",at = @At("HEAD"), cancellable = true)
    private void onApplyStatusEffectOffset(CallbackInfo c){
        if (!this.client.player.getStatusEffects().isEmpty() && SEPConfig.invEffects != SEPConfig.InvEffects.VANILLA) {
            this.x = (this.width - this.backgroundWidth) / 2;
            this.drawStatusEffects = true;
            c.cancel();
        }
    }

    @Inject(method = "drawStatusEffects",at = @At("HEAD"), cancellable = true)
    private void onDrawStatusEffects(MatrixStack matrixStack, CallbackInfo c) {
        if(MinecraftClient.getInstance().player != null){
            if(SEPConfig.invEffects != SEPConfig.InvEffects.VANILLA){
                if(SEPConfig.invEffects == SEPConfig.InvEffects.MODDED){
                    statusEffectHUDFeature.render(matrixStack, MinecraftClient.getInstance().player.getStatusEffects());
                }
                c.cancel();
            }

        }
    }

    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

    }
}
