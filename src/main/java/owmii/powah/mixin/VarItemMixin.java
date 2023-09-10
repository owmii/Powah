package owmii.powah.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import owmii.lib.item.VarItem;
import owmii.lib.registry.IVariant;

import static owmii.powah.utils.GetTieredTranslatedName.getTieredTranslatedName;

@Mixin(Item.class)
public class VarItemMixin {
    @Inject(at = @At(value = "HEAD"), method = "getDisplayName", cancellable = true)
    @SuppressWarnings("rawtypes")
    protected void onGetDisplayName(ItemStack stack, CallbackInfoReturnable<ITextComponent> cir) {
        Item obj = (Item)(Object)this;
        if (!(obj instanceof VarItem) || I18n.hasKey(obj.getTranslationKey())) return;

        cir.cancel();
        cir.setReturnValue(getTieredTranslatedName(obj, (IVariant)(((VarItem)obj).getVariant())));
    }
}
