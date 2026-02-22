package eu.gflash.quickcraft.mixin;

import eu.gflash.quickcraft.client.InputHelper;
import eu.gflash.quickcraft.client.InventoryHelper;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.CraftingMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingMenu.class)
public class CraftingMenuMixin {
	@Inject(method = "slotsChanged", at = @At("RETURN"))
	private void onContentChanged(Container inventory, CallbackInfo ci){
		if(InputHelper.isCtrlPressed()) {
			InventoryHelper.scheduleCraft();
		}
	}
}
