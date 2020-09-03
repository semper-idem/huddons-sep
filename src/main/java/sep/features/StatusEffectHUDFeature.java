package sep.features;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.util.Identifier;
import sep.config.SEPConfig;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;



@Environment(EnvType.CLIENT)
public class StatusEffectHUDFeature extends DrawableHelper{
    private final static Identifier NUMS = new Identifier("sep", "textures/nums.png");
    private final static MinecraftClient client = MinecraftClient.getInstance();
    private int scaledWidth = client.getWindow().getScaledWidth();
    private int scaledHeight = client.getWindow().getScaledHeight();
    private int size = 0;

    public void render(MatrixStack matrixStack, Collection<StatusEffectInstance> statusEffects) {
        if(!statusEffects.isEmpty()) {
            updateConfig();

            int x = (int) (scaledWidth  * SEPConfig.getXOffset());
            int y = (int) (scaledHeight * SEPConfig.getYOffset());
            this.size = SEPConfig.getSize();

            List<Runnable> list = Lists.newArrayListWithExpectedSize(statusEffects.size());
            Iterator<?> it = Ordering.natural().reverse().sortedCopy(statusEffects).iterator();
            int h = 0;
            int w = 0;
            int i = 0;
            while(it.hasNext()) {
                StatusEffectInstance sei = (StatusEffectInstance) it.next();

                int length = getSEWidth(sei);
                int x0 = x - SEPConfig.getXAlignment() * (length / 2) + ((SEPConfig.getXSpace() * i + w) * SEPConfig.getXArrangement());
                int y0 = y                                            + ((SEPConfig.getYSpace() * i + h) * SEPConfig.getYArrangement());

                list.add(() -> {
                    renderSEBackground(matrixStack,sei, x0, y0, length);
                    renderSEIcon(matrixStack, sei, x0, y0, length);
                    renderSEText(matrixStack, sei, x0, y0, length);
                });
                w += length;
                h += this.size + 2;
                i++;
            }
            list.forEach(Runnable::run);
        }
    }

    private void updateConfig(){
        if(SEPConfig.isDirty()){
            SEPConfig.update();
        }
    }

    private void renderSEBackground(MatrixStack matrixStack, StatusEffectInstance sei, int x, int y, int length){

        int x1 = x - (length / 2);
        int x2 = x + (length / 2);
        int y2 = y + 2 + this.size;
        if(SEPConfig.getMode() == 1){
            RenderSystem.enableBlend();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.65F);
            client.getTextureManager().bindTexture(HandledScreen.BACKGROUND_TEXTURE);
            if (sei.isAmbient()) {
                drawTexture(matrixStack, x1, y,3,20, 165, 166, 3, 24, 256, 256);
                drawTexture(matrixStack, x1 + 3, y,length - 6,20, 168, 166, 18, 24, 256, 256);
                drawTexture(matrixStack, x1 + length - 3, y,3,20, 186, 166, 3, 24, 256, 256);
            } else {
                drawTexture(matrixStack, x1, y,3,20, 141, 166, 3, 24, 256, 256);
                drawTexture(matrixStack, x1 + 3, y,length - 6,20, 144, 166, 18, 24, 256, 256);
                drawTexture(matrixStack, x1 + length - 3, y,3,20, 162, 166, 3, 24, 256, 256);
            }
            RenderSystem.disableBlend();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        } else {
            fill(matrixStack, x1, y,x2,y2, SEPConfig.getBackgroundColor());
        }
    }

    private void renderSEIcon(MatrixStack matrixStack, StatusEffectInstance sei, int x, int y, int length){
        int x0;
        int y0;

        if(SEPConfig.getMode() == 1){
            x0 = x - 8;
            y0 = y - 3;
        } else {
            x0 = x + 2 - (length / 2);
            y0 = y + 1;
        }

        Sprite sprite = client.getStatusEffectSpriteManager().getSprite(sei.getEffectType());
        client.getTextureManager().bindTexture(sprite.getAtlas().getId());
        drawSprite(matrixStack, x0, y0, 1, this.size, this.size, sprite);

        if(SEPConfig.getMode() == 1){
            renderAmplifier(matrixStack, x0 + 13, y0, sei.getAmplifier() + 1);
        }
    }

    private void renderAmplifier(MatrixStack matrixStack, int x, int y, int lvl){
        client.getTextureManager().bindTexture(NUMS);
        String number = String.valueOf(lvl);
        char[] digits = number.toCharArray();
        for(int i = digits.length - 1; i >= 0; i--){
            drawTexture(matrixStack,x + (4 * (i - digits.length + 1)),y + 5,  (digits[i]+2) * 8,1,5,7,80,8);
        }
    }

    private void renderSEText(MatrixStack matrixStack, StatusEffectInstance sei, int x, int y, int length){

        String nameString = getSEText(sei);
        String durationString = getSEDuration(sei);
        int nameColor = SEPConfig.getNameColor();
        int durationColor = SEPConfig.getDurationColor();

        int x0 = x + 4 + this.size - (length / 2);
        int y0 = y + 3;
        int x1 = x0;

        if(SEPConfig.getAutoColor() == 1){
            nameColor = sei.getEffectType().getColor();
            durationColor = nameColor;
        }

        if(SEPConfig.getMode() == 1){
            drawStringWithShadow(matrixStack,client.textRenderer, durationString, x - (length /2) + 4, y0+7, durationColor);
        } else {
            x1 = x1 + client.textRenderer.getWidth(nameString + " ");
            drawStringWithShadow(matrixStack,client.textRenderer, nameString, x0, y0, nameColor);
            drawStringWithShadow(matrixStack,client.textRenderer, durationString, x1, y0, durationColor);
        }

    }

    private String getSEText(StatusEffectInstance sei ) {
        return (sei.getAmplifier() >= 0 && sei.getAmplifier() <= 9) ?
                I18n.translate(sei.getEffectType().getTranslationKey()) + " " + I18n.translate("enchantment.level." + (sei.getAmplifier() + 1)) :
                I18n.translate(sei.getEffectType().getTranslationKey());
    }

    private String getSEDuration(StatusEffectInstance sei) {
        String duration = StatusEffectUtil.durationToString(sei, 1.0F);
        return sei.isAmbient() && SEPConfig.getMode() != 1 ? "* " + duration : duration;
        //cant show more then 32767 ticks cause its short somewhere...
    }

    private int getSEWidth(StatusEffectInstance sei){
        String s = "  ";
        if(SEPConfig.getMode() == 1){
            s += getSEDuration(sei);
        } else {
            s += getSEText(sei) + getSEDuration(sei) + this.size + " ";
        }
        return Math.max(client.textRenderer.getWidth("  0:00"), client.textRenderer.getWidth(s));
    }
}
