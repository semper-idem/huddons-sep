package sep.config.widgets;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WAbstractSlider;
import io.github.cottonmc.cotton.gui.widget.WLabeledSlider;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class WSEPLabeledSlider extends WLabeledSlider {
    private String label;
    private HorizontalAlignment labelAlignment;
    private boolean percentage = false;
    private boolean pendingDraggingFinishedFromScrolling;

    private WSEPLabeledSlider(int min, int max, Axis axis) {
        super(min, max, axis);
        this.label = null;
        this.labelAlignment = HorizontalAlignment.CENTER;
    }

    public WSEPLabeledSlider(int min, int max, String label, boolean percentage) {
        this(min, max, Axis.HORIZONTAL);
        this.label = label;
        this.percentage = percentage;
    }

    public WSEPLabeledSlider(int min, int max, String label) {
        this(min, max, Axis.HORIZONTAL);
        this.label = label;

    }    
    public void onMouseScroll(int x, int y, double amount) {
        if (this.direction == WAbstractSlider.Direction.LEFT || this.direction == WAbstractSlider.Direction.DOWN) {
            amount = -amount;
        }

        int previous = this.value;
        int next = this.value + (int) (amount / Math.abs(amount));
        if(next >= min && next <= max){
            this.value = next;
            if (previous != this.value) {
                this.onValueChanged(this.value);
                this.pendingDraggingFinishedFromScrolling = true;
            }
        }

    }

    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        int aWidth = this.axis == Axis.HORIZONTAL ? this.width : this.height;
        int aHeight = this.axis == Axis.HORIZONTAL ? this.height : this.width;
        int rotMouseX = this.axis == Axis.HORIZONTAL ? (this.direction == Direction.LEFT ? this.width - mouseX : mouseX) : (this.direction == Direction.UP ? this.height - mouseY : mouseY);
        int rotMouseY = this.axis == Axis.HORIZONTAL ? mouseY : mouseX;
        matrices.push();
        matrices.translate(x, y, 0.0D);
        this.drawButton(matrices,0, 0, 0, aWidth);
        int thumbX = Math.round(this.coordToValueRatio * (float)(this.value - this.min));
        int thumbY = 0;
        int thumbWidth = this.getThumbWidth();
        boolean hovering = rotMouseX >= thumbX && rotMouseX <= thumbX + thumbWidth && rotMouseY >= thumbY && rotMouseY <= thumbY + aHeight;
        int thumbState = !this.dragging && !hovering ? 1 : 2;
        this.drawButton(matrices,thumbX, thumbY, thumbState, thumbWidth);
        if (this.label != null) {
            int color = this.isMouseInsideBounds(mouseX, mouseY) ? 16777120 : 14737632;
            String text = this.label + this.value;
            if(percentage){
                text = this.label + ((float)this.value/10) + "%";
            }
            ScreenDrawing.drawStringWithShadow(matrices, text, this.labelAlignment, 2, aHeight / 2 - 4, aWidth - 4, color);
        }

        matrices.pop();
    }
    @Environment(EnvType.CLIENT)
    private void drawButton(MatrixStack matrices, int x, int y, int state, int width) {
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
    }

}
