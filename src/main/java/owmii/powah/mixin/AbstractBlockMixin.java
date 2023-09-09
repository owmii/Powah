package owmii.powah.mixin;

import java.util.Arrays;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import owmii.lib.block.AbstractBlock;
import owmii.powah.Powah;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
    @Inject(at = @At(value = "HEAD"), method = "getDisplayName", cancellable = true)
    @SuppressWarnings("rawtypes")
    protected void onGetDisplayName(ItemStack stack, CallbackInfoReturnable<IFormattableTextComponent> cir) {
        Block obj = (AbstractBlock)(Object)this;
        if (I18n.hasKey(obj.getTranslationKey())) return;

        cir.cancel();
        String[] name = obj.getRegistryName().getPath().split("_");
        cir.setReturnValue(new TranslationTextComponent("block.powah."+String.join("_", Arrays.copyOf(name, name.length-1))).appendString(" (").append(new TranslationTextComponent("tier."+Powah.MOD_ID+"."+((AbstractBlock)obj).getVariant().getName()).appendString(")")));
    }
}
