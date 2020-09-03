package sep.config.screen;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import sep.config.SEPConfig;
import sep.config.widgets.WSEPColorPreview;
import sep.config.widgets.WSEPLabeledSlider;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class SEPColorPickerGui extends EmptyGuiDescription {
    private static final Map<String, String> pickers = new HashMap<>();
    private final static int buttonSpace = 24;
    static{
        pickers.put("color_background", "Background Color");
        pickers.put("color_duration", "Duration Color");
        pickers.put("color_name", "Name Color");
    }
    private int[] argb = new int[4];

    public SEPColorPickerGui(String key){
        int width = 310;
        int height = MinecraftClient.getInstance().getWindow().getScaledHeight();
        int labelSpace = (height - buttonSpace * 8) > 40 ? (height - buttonSpace * 8 - 20) / 6 : 20;
        setARGB(SEPConfig.getProperty(key));
        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        root.setSize(width,height);

        WLabel sepLabel = new WLabel(pickers.get(key), 0xFFFFFFFF).setHorizontalAlignment(HorizontalAlignment.CENTER);
        WSEPColorPreview colorPreview = new WSEPColorPreview(SEPConfig.getProperty(key), 150, 92,key);
        WSEPLabeledSlider alphaSlider = getColorSlider(0, "Alpha: ", colorPreview);
        WSEPLabeledSlider redSlider = getColorSlider(1, "Red: ", colorPreview);
        WSEPLabeledSlider greenSlider = getColorSlider(2, "Green: ", colorPreview);
        WSEPLabeledSlider blueSlider = getColorSlider(3, "Blue: ", colorPreview);
        WButton cancelButton = getCancelButton();
        WButton doneButton = getDoneButton(key);

        root.add(sepLabel,       0, 15, width, 4);
        root.add(alphaSlider,    0,labelSpace + buttonSpace, 150, 4);
        root.add(redSlider,      0,labelSpace + buttonSpace * 2, 150, 4);
        root.add(greenSlider,    0,labelSpace + buttonSpace * 3, 150, 4);
        root.add(blueSlider,     0,labelSpace + buttonSpace * 4, 150, 4);
        root.add(cancelButton,  55,labelSpace + buttonSpace * 5, 200, 4);
        root.add(doneButton,    55,labelSpace + buttonSpace * 6, 200, 4);
        root.add(colorPreview, 160,labelSpace + buttonSpace, 150, 92);

        root.validate(this);
    }

    private WButton getCancelButton(){
        WButton cancelButton = new WButton(Text.of("Cancel"));
        cancelButton.setOnClick(()->{
            MinecraftClient.getInstance().openScreen(new SEPConfigScreen(new SEPConfigGui()));
        });
        return cancelButton;
    }

    private WButton getDoneButton(String key){
        WButton doneButton = new WButton(Text.of("Apply"));
        doneButton.setOnClick(()->{
            SEPConfig.setProperty(key, String.valueOf(getARGB()));
            MinecraftClient.getInstance().openScreen(new SEPConfigScreen(new SEPConfigGui()));
        });
        return doneButton;
    }

    private WSEPLabeledSlider getColorSlider(int colorIndex, String label, WSEPColorPreview display){
        WSEPLabeledSlider slider = new WSEPLabeledSlider(0, 255, label);
        slider.setValue(argb[colorIndex]);
        slider.setLabelUpdater(labelUpdater ->{
            argb[colorIndex] = labelUpdater;
            display.set(colorIndex, labelUpdater);
            return null;
        });
        return slider;
    }

    private void setARGB(int color){
        argb[0] = (color >> 24) & 255;
        argb[1] = (color >> 16) & 255;
        argb[2] = (color >> 8)  & 255;
        argb[3] =  color        & 255;
    }

    private int getARGB(){
        return  ((argb[0] & 0xFF) << 24) |
                ((argb[1] & 0xFF) << 16) |
                ((argb[2] & 0xFF) << 8)  |
                ((argb[3] & 0xFF));
    }
}
