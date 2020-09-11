package sep.config.screen;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import sep.config.SEPConfig;
import sep.config.widgets.WSEPColorPickerButton;
import sep.config.widgets.WSEPCycleButton;
import sep.config.widgets.WSEPLabeledSlider;

@Environment(EnvType.CLIENT)
public class SEPConfigGui extends EmptyGuiDescription {
    private final static int buttonSpace = 24;
    public SEPConfigGui(){
        int width = 310;
        int height = MinecraftClient.getInstance().getWindow().getScaledHeight();
        int labelSpace = (height - buttonSpace * 8) > 40 ? (height - buttonSpace * 8 - 20) / 6 : 20;
        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        root.setSize(width,height);

        WLabel sepLabel = new WLabel("  Status Effects+", 0xFFFFFFFF).setHorizontalAlignment(HorizontalAlignment.CENTER);
        WSEPLabeledSlider xOffset = (getSlider("X Offset: ", "x_offset", 1000,true));
        WSEPLabeledSlider yOffset = (getSlider("Y Offset: ", "y_offset", 1000,true));
        WSEPCycleButton xAlignment = (new WSEPCycleButton("x_alignment", new String[]{"Left", "Center", "Right"}, "X Alignment: "));
        WSEPCycleButton xArrangement = (new WSEPCycleButton("x_arrangement", new String[]{"Left", "Center", "Right"}, "X Arrangement: "));
        WSEPCycleButton yArrangement = (new WSEPCycleButton("y_arrangement", new String[]{"Up","Center", "Down"}, "Y Arrangement: "));
        WSEPCycleButton mode = (new WSEPCycleButton("mode", new String[]{"Plain", "Fancy"}, "Mode: "));
        WSEPLabeledSlider xSpace = (getSlider("X Space: ", "x_space", 20,false));
        WSEPLabeledSlider ySpace = (getSlider("Y Space: ", "y_space", 20,false));
        WSEPCycleButton colorAuto = (new WSEPCycleButton("color_auto", new String[]{"Off", "On"}, "Auto-Color: "));
        WSEPCycleButton potionGlint = (new WSEPCycleButton("potion_glint", new String[]{"Off", "On"}, "Potion Glint: "));
        WSEPColorPickerButton backgroundColorPicker = new WSEPColorPickerButton("color_background", "Background Color");
        WSEPColorPickerButton durationColorPicker = new WSEPColorPickerButton("color_duration", "Duration Color");
        WSEPColorPickerButton textColorPicker = new WSEPColorPickerButton("color_name", "Name Color");

        WButton resetToDefaultButton = getResetToDefaultButton();
        WButton doneButton = getDoneButton();

        root.add(sepLabel, 0, 15, 310, 4);
        root.add(xOffset,0,labelSpace + buttonSpace, 150, 20);
        root.add(yOffset,0,labelSpace + buttonSpace * 2, 150, 20);
        root.add(xAlignment,0,labelSpace + buttonSpace * 3, 150, 20);
        root.add(xArrangement,0,labelSpace + buttonSpace * 4, 150, 20);
        root.add(yArrangement,0,labelSpace + buttonSpace * 5, 150, 20);
        root.add(xSpace,0,labelSpace + buttonSpace * 6, 150, 20);
        root.add(ySpace,0,labelSpace + buttonSpace * 7, 150, 20);
        root.add(colorAuto,160,labelSpace + buttonSpace, 150, 20);
        root.add(backgroundColorPicker, 160,labelSpace + buttonSpace * 2, 150, 20);
        root.add(textColorPicker, 160,labelSpace + buttonSpace * 3, 150, 20);
        root.add(durationColorPicker, 160,labelSpace + buttonSpace * 4, 150, 20);
        root.add(mode,160,labelSpace + buttonSpace * 5, 150, 20);
        root.add(potionGlint, 160,labelSpace + buttonSpace * 6, 150, 20);
        root.add(resetToDefaultButton, 160,labelSpace + buttonSpace * 7, 150, 20);
        root.add(doneButton, 55, labelSpace + buttonSpace * 8, 200, 4);

        root.validate(this);

    }

    private WButton getDoneButton(){
        return new WButton(Text.of("Done")).setOnClick(()->{
            MinecraftClient.getInstance().openScreen((Screen)null);
            MinecraftClient.getInstance().mouse.lockCursor();
        });

    }

    private WButton getResetToDefaultButton(){
        return new WButton(Text.of("Reset To Default")).setOnClick(()->{
           SEPConfig.setDefaultProperties();
           MinecraftClient.getInstance().openScreen((Screen)null);
           MinecraftClient.getInstance().mouse.lockCursor();
        });
    }

    private WSEPLabeledSlider getSlider(String label, String key, int max, boolean percentage){
        WSEPLabeledSlider slider = new WSEPLabeledSlider(0, max, label, percentage);
        slider.setValue(SEPConfig.getProperty(key));
        slider.setLabelUpdater(i -> {
            SEPConfig.setProperty(key, String.valueOf(i));
            return null;
        });
        return slider;
    }



}
