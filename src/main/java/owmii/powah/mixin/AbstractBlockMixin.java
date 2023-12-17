package owmii.powah.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import owmii.lib.block.AbstractBlock;

import static owmii.powah.utils.GetTieredTranslatedName.getTieredTranslatedName;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
    @Inject(at = @At(value = "HEAD"), method = "getDisplayName", cancellable = true, remap = false)
    @SuppressWarnings("rawtypes")
    protected void onGetDisplayName(ItemStack stack, CallbackInfoReturnable<IFormattableTextComponent> cir) {
        AbstractBlock obj = (AbstractBlock)(Object)this;
        if (I18n.hasKey(obj.getTranslationKey())) return;

        cir.cancel();
        cir.setReturnValue(getTieredTranslatedName(obj, ((AbstractBlock)obj).getVariant()));
    }
}
