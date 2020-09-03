package sep.config.widgets;

import io.github.cottonmc.cotton.gui.widget.WWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import sep.config.SEPConfig;

@Environment(EnvType.CLIENT)
public class WSEPColorPreview extends WWidget {
    private static Identifier mcbg = new Identifier("sep:textures/widget/mcbg.png");
    private static Text testText = Text.of("Status Effect");
    private static int testTextWidth = MinecraftClient.getInstance().textRenderer.getWidth("Status Effect");

    private int color;
    private int[] argb = new int[4];
    private String key;

    public WSEPColorPreview(int color, int width, int height, String key) {
        this.key = key;
        this.color = color;
        this.width = width;
        this.height = height;
        setARGB(color);
    }


    public void set(int colorIndex, int value) {
        argb[colorIndex] = value;
        update();
    }
    private void update() {
        this.color = getARGB();
    }

    private void setARGB(int color) {
        argb[0] = (color >> 24) & 255;
        argb[1] = (color >> 16) & 255;
        argb[2] = (color >> 8) & 255;
        argb[3] = color & 255;
    }


    private int getARGB() {
        return ((argb[0] & 0xFF) << 24) |
                ((argb[1] & 0xFF) << 16) |
                ((argb[2] & 0xFF) << 8) |
                ((argb[3] & 0xFF));
    }

    @Override
    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(mcbg);
        DrawableHelper.drawTexture(matrices, x, y, 0, 0, 0, width, height, 256, 512);
        if(key.equals("color_background")){
            DrawableHelper.fill(matrices, x, y + (height / 3), x + width, y + (height * 2 / 3), color);
        } else {
            DrawableHelper.fill(matrices, x, y + (height / 3), x + width, y + (height * 2 / 3), SEPConfig.getProperty("color_background"));
            DrawableHelper.drawTextWithShadow(matrices, MinecraftClient.getInstance().textRenderer, testText, x + ((width / 2) - (testTextWidth / 2)), y + height / 3 + (height / 6) - 4, color);

        }
        }
}
