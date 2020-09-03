package sep.config.screen;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class EmptyGuiDescription extends LightweightGuiDescription {
    @Environment(EnvType.CLIENT)
    @Override
    public void addPainters() {
    }
}
