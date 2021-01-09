package sep.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class SEPConfigScreen {
    public static Screen getConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTransparentBackground(true)
                .setTitle(new TranslatableText("config.sep.title"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("config.sep.category.general"));

        general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.sep.potionGlint"), SEPConfig.potionGlint)
                .setDefaultValue(false)
                .setSaveConsumer(value -> SEPConfig.potionGlint = value)
                .setTooltip(new TranslatableText("config.sep.potionGlint.tooltip"))
                .build());

        general.addEntry(entryBuilder.startSelector(new TranslatableText("config.sep.mode"), SEPConfig.Mode.values(), SEPConfig.mode)
                .setDefaultValue(SEPConfig.Mode.CLASSIC)
                .setNameProvider(value ->{
                    if(value.equals(SEPConfig.Mode.CLASSIC)){
                        return new TranslatableText("config.sep.mode.classic");
                    } else if(value.equals(SEPConfig.Mode.INLINE)){
                        return new TranslatableText("config.sep.mode.inline");
                    }
                    return new LiteralText("Error");
                })
                .setSaveConsumer(value -> SEPConfig.mode = value)
                .setTooltip(new TranslatableText("config.sep.mode.tooltip"))
                .build());

        general.addEntry(entryBuilder.startFloatField(new TranslatableText("config.sep.xOffset"), SEPConfig.xOffset)
                .setDefaultValue(99.9f)
                .setMax(0)
                .setMax(100)
                .setSaveConsumer(value -> SEPConfig.xOffset = value)
                .setTooltip(new TranslatableText("config.sep.xOffset.tooltip"))
                .build());

        general.addEntry(entryBuilder.startFloatField(new TranslatableText("config.sep.yOffset"), SEPConfig.yOffset)
                .setDefaultValue(0.75f)
                .setMax(0)
                .setMax(100)
                .setSaveConsumer(value -> SEPConfig.yOffset = value)
                .setTooltip(new TranslatableText("config.sep.yOffset.tooltip"))
                .build());

        general.addEntry(entryBuilder.startSelector(new TranslatableText("config.sep.xAlignment"), SEPConfig.XAxis.values(), SEPConfig.xAlignment)
                .setDefaultValue(SEPConfig.XAxis.RIGHT)
                .setNameProvider(value ->{
                    if(value.equals(SEPConfig.XAxis.RIGHT)){
                        return new TranslatableText("config.sep.axis.right");
                    } else if(value.equals(SEPConfig.XAxis.CENTER)){
                        return new TranslatableText("config.sep.axis.center");
                    }else if(value.equals(SEPConfig.XAxis.LEFT)){
                        return new TranslatableText("config.sep.axis.left");
                    }
                    return new LiteralText("Error");
                })
                .setSaveConsumer(value -> SEPConfig.xAlignment = value)
                .setTooltip(new TranslatableText("config.sep.xAlignment.tooltip"))
                .build());
        general.addEntry(entryBuilder.startSelector(new TranslatableText("config.sep.xArrangement"), SEPConfig.XAxis.values(), SEPConfig.xArrangement)
                .setDefaultValue(SEPConfig.XAxis.LEFT)
                .setNameProvider(value ->{
                    if(value.equals(SEPConfig.XAxis.RIGHT)){
                        return new TranslatableText("config.sep.axis.right");
                    } else if(value.equals(SEPConfig.XAxis.CENTER)){
                        return new TranslatableText("config.sep.axis.center");
                    }else if(value.equals(SEPConfig.XAxis.LEFT)){
                        return new TranslatableText("config.sep.axis.left");
                    }
                    return new LiteralText("Error");
                })
                .setSaveConsumer(value -> SEPConfig.xArrangement = value)
                .setTooltip(new TranslatableText("config.sep.xArrangement.tooltip"))
                .build());
        general.addEntry(entryBuilder.startSelector(new TranslatableText("config.sep.yArrangement"), SEPConfig.YAxis.values(), SEPConfig.yArrangement)
                .setDefaultValue(SEPConfig.YAxis.CENTER)
                .setNameProvider(value ->{
                    if(value.equals(SEPConfig.YAxis.UP)){
                        return new TranslatableText("config.sep.axis.up");
                    } else if(value.equals(SEPConfig.YAxis.CENTER)){
                        return new TranslatableText("config.sep.axis.center");
                    }else if(value.equals(SEPConfig.YAxis.DOWN)){
                        return new TranslatableText("config.sep.axis.down");
                    }
                    return new LiteralText("Error");
                })
                .setSaveConsumer(value -> SEPConfig.yArrangement = value)
                .setTooltip(new TranslatableText("config.sep.yArrangement.tooltip"))
                .build());
        general.addEntry(entryBuilder.startIntField(new TranslatableText("config.sep.xSpace"), SEPConfig.xSpace)
                .setDefaultValue(2)
                .setMin(0)
                .setSaveConsumer(value -> SEPConfig.xSpace = value)
                .setTooltip(new TranslatableText("config.sep.xSpace.tooltip"))
                .build());
        general.addEntry(entryBuilder.startIntField(new TranslatableText("config.sep.ySpace"), SEPConfig.ySpace)
                .setDefaultValue(0)
                .setMin(0)
                .setSaveConsumer(value -> SEPConfig.ySpace = value)
                .setTooltip(new TranslatableText("config.sep.ySpace.tooltip"))
                .build());
        general.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.sep.colorAuto"), SEPConfig.colorAuto)
                .setDefaultValue(false)
                .setSaveConsumer(value -> SEPConfig.colorAuto = value)
                .setTooltip(new TranslatableText("config.sep.colorAuto.tooltip"))
                .build());

        general.addEntry(entryBuilder.startColorField(new TranslatableText("config.sep.colorBackground"), SEPConfig.colorBackground)
                .setDefaultValue(0x30000000)
                .setAlphaMode(true)
                .setSaveConsumer(value -> SEPConfig.colorBackground = value)
                .setTooltip(new TranslatableText("config.sep.colorBackground.tooltip"))
                .build());

        general.addEntry(entryBuilder.startColorField(new TranslatableText("config.sep.colorName"), SEPConfig.colorName)
                .setDefaultValue(0x80FFFFFF)
                .setAlphaMode(true)
                .setSaveConsumer(value -> SEPConfig.colorName = value)
                .setTooltip(new TranslatableText("config.sep.colorName.tooltip"))
                .build());

        general.addEntry(entryBuilder.startColorField(new TranslatableText("config.sep.colorDuration"), SEPConfig.colorDuration)
                .setDefaultValue(0xaaFFFFFF)
                .setAlphaMode(true)
                .setSaveConsumer(value -> SEPConfig.colorDuration = value)
                .setTooltip(new TranslatableText("config.sep.colorDuration.tooltip"))
                .build());


        general.addEntry(entryBuilder.startSelector(new TranslatableText("config.sep.invEffects"), SEPConfig.InvEffects.values(), SEPConfig.invEffects)
                .setDefaultValue(SEPConfig.InvEffects.MODDED)
                .setNameProvider(value ->{
                    if(value.equals(SEPConfig.InvEffects.MODDED)){
                        return new TranslatableText("config.sep.invEffects.modded");
                    } else if(value.equals(SEPConfig.InvEffects.VANILLA)){
                        return new TranslatableText("config.sep.invEffects.vanilla");
                    }else if(value.equals(SEPConfig.InvEffects.NONE)){
                        return new TranslatableText("config.sep.invEffects.none");
                    }
                    return new LiteralText("Error");
                })
                .setSaveConsumer(value -> SEPConfig.invEffects = value)
                .setTooltip(new TranslatableText("config.sep.invEffects.tooltip"))
                .build());


        return builder.setSavingRunnable(()->{
            try {
                SEPConfig.saveConfig();
            } catch (Exception e) {
                e.printStackTrace();
            }
            SEPConfig.loadConfig();
        }).build();
    }
}
