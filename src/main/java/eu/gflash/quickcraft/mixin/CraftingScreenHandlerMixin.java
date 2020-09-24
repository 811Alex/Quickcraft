package eu.gflash.quickcraft.mixin;

import eu.gflash.quickcraft.client.InventoryHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingScreenHandler.class)
public class CraftingScreenHandlerMixin {

	@Inject(method = "updateResult", at = @At("RETURN"))
	private static void updateResult(int syncId, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory, CallbackInfo ci){
		if(InventoryHelper.isValidResult(resultInventory)){	// resultInventory not empty & resultInventory == clicked recipe (the items get put in the crafting table gradually, we don't want any recipes that are craftable in-between)
			InventoryHelper.scheduleCraft();
		}
	}
}
