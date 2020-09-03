package sep.config.widgets;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import sep.config.screen.SEPColorPickerGui;
import sep.config.SEPConfig;
import sep.config.screen.SEPConfigScreen;

@Environment(EnvType.CLIENT)
public class WSEPColorPickerButton extends WButton {
    private int color;
    private String key;
    private String label;

    public WSEPColorPickerButton(String key, String label) {
        super(Text.of(key));
        this.key = key;
        this.label = label;
        this.color = SEPConfig.getProperty(key);
    }

    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        boolean hovered = mouseX >= 0 && mouseY >= 0 && mouseX < this.getWidth() && mouseY < this.getHeight();
        int state = 1;
        if (hovered || this.isFocused()) {
            state = 2;
        } else if((!this.key.equals("color_background") && SEPConfig.getProperty("color_auto") == 1)){
            state = 0;
        }
        float buttonLeft = 0.0F;
        float buttonTop = (float)(46 + state * 20);
        int halfWidth = width / 2;
        if (halfWidth > 198) {
            halfWidth = 198;
        }
        int buttonWidth = halfWidth;
        int buttonHeight = 20;
        float buttonEndLeft = (float)(200 - halfWidth) ;
        MinecraftClient.getInstance().getTextureManager().bindTexture(AbstractButtonWidget.WIDGETS_LOCATION);
        DrawableHelper.drawTexture(matrices, x, y, halfWidth, 20, buttonLeft,buttonTop, buttonWidth,buttonHeight , 256,256);
        DrawableHelper.drawTexture(matrices, x + halfWidth, y, halfWidth, 20, buttonEndLeft,buttonTop, buttonWidth,buttonHeight , 256,256);
        if(state != 0){
            DrawableHelper.fill(matrices, x + 3,y + 3,x+width - 3, y+17, color);
        }
        ScreenDrawing.drawStringWithShadow(matrices, label, HorizontalAlignment.CENTER, x, y+6, this.width, 0xFFFFFFFF);
    }

    public void onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if ((this.key.equals("color_background") || SEPConfig.getProperty("color_auto") != 1) && this.isWithinBounds(x, y)) {
            MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            MinecraftClient.getInstance().openScreen(new SEPConfigScreen(new SEPColorPickerGui(this.key)));
        }

    }
}
