package sep.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.PotionUtil;
import org.spongepowered.asm.mixin.Mixin;
import sep.config.SEPConfig;


@Mixin(PotionItem.class)
public class PotionItemMixin extends Item {

    public PotionItemMixin(Settings settings) {
        super(settings);
    }

    public boolean hasGlint(ItemStack stack) {
        if(SEPConfig.potionGlint){
            return super.hasGlint(stack) || !PotionUtil.getPotionEffects(stack).isEmpty();
        } else {
            return false;
        }
    }
}
