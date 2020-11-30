package sep;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import sep.config.SEPConfig;
import sep.config.SEPConfigScreen;

@Environment(EnvType.CLIENT)
public class SEPInitializer implements ClientModInitializer {
    private static  KeyBinding configKeyBinding;

    @Override
    public void onInitializeClient() {
        SEPConfig.loadConfig();
        configKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.sep.name", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_KP_2, "key.sep.category"));
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            while(configKeyBinding.isPressed()){
                MinecraftClient.getInstance().openScreen(SEPConfigScreen.getConfigScreen(MinecraftClient.getInstance().currentScreen));
            }
        });
    }
}