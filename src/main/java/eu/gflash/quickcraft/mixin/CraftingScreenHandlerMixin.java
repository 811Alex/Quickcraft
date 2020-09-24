package eu.gflash.quickcraft.mixin;

import eu.gflash.quickcraft.client.InventoryHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingScreenHandler.class)
public class CraftingScreenHandlerMixin {

	@Inject(method = "onContentChanged", at = @At("RETURN"))
	private void onContentChanged(Inventory inventory, CallbackInfo ci){
		if(Screen.hasControlDown()) {
			InventoryHelper.scheduleCraft();
		}
	}
}
