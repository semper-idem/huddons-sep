package sep.config.widgets;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WButton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import sep.config.SEPConfig;

@Environment(EnvType.CLIENT)
public class WSEPCycleButton extends WButton {
    private String[] states;
    private int state;
    private String key;
    private String label;

    public WSEPCycleButton(String key, String[] states, String label){
        super();
        this.states = states;
        this.state = SEPConfig.getProperty(key);
        this.key = key;
        this.label = label;
    }
    @Override
    public void onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if(state >= states.length - 1){
            state = 0;
        } else {
            state++;
        }
        SEPConfig.setProperty(key, String.valueOf(state));
    }

    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        boolean hovered = mouseX >= 0 && mouseY >= 0 && mouseX < this.getWidth() && mouseY < this.getHeight();
        int s = 1;
        if (hovered || this.isFocused()) {
            s = 2;
        }

        float px = 0.00390625F;
        float buttonLeft = 0.0F * px;
        float buttonTop = (float)(46 + s * 20) * px;
        int halfWidth = this.getWidth() / 2;
        if (halfWidth > 198) {
            halfWidth = 198;
        }

        float buttonWidth = (float)halfWidth * px;
        float buttonHeight = 20.0F * px;
        float buttonEndLeft = (float)(200 - this.getWidth() / 2) * px;
        ScreenDrawing.texturedRect(x, y, this.getWidth() / 2, 20, AbstractButtonWidget.WIDGETS_LOCATION, buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonHeight, -1);
        ScreenDrawing.texturedRect(x + this.getWidth() / 2, y, this.getWidth() / 2, 20, AbstractButtonWidget.WIDGETS_LOCATION, buttonEndLeft, buttonTop, 200.0F * px, buttonTop + buttonHeight, -1);

        if (this.key != null) {
            int color = 14737632;
            ScreenDrawing.drawStringWithShadow(matrices, label + getStateName(), this.alignment, x, y + 6, this.width, color);
        }

    }
    private String getStateName(){
        return states[state];
    }
}
