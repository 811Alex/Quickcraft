package eu.gflash.quickcraft.mixin;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeBookResults.class)
public abstract class RecipeClickMixin {
	@Shadow private RecipeResultCollection resultCollection;
	@Shadow private Recipe<CraftingInventory> lastClickedRecipe;

	@Shadow public abstract MinecraftClient getMinecraftClient();

	@Inject(method = "mouseClicked", at = @At("RETURN"))
	protected void mouseClicked(double mouseX, double mouseY, int button, int areaLeft, int areaTop, int areaWidth, int areaHeight, CallbackInfoReturnable<Boolean> cir){
		craft();
	}

	protected void craft(){
		if(Screen.hasControlDown() && resultCollection != null && resultCollection.isCraftable(lastClickedRecipe)){
			ClientPlayerEntity ply = getMinecraftClient().player;
			ClientPlayerInteractionManager im = getMinecraftClient().interactionManager;
			if(im == null || ply == null || getMinecraftClient().currentScreen == null) return;
			PlayerInventory inv = ply.inventory;
			ScreenHandler csh = ply.currentScreenHandler;
			if(csh instanceof CraftingScreenHandler || csh instanceof PlayerScreenHandler){
				int resultSlotIndex = ((AbstractRecipeScreenHandler<?>) csh).getCraftingResultSlotIndex();
				ItemStack outStack = csh.slots.get(resultSlotIndex).getStack();
				InputUtil.Key keyDrop = KeyBindingHelper.getBoundKeyOf(getMinecraftClient().options.keyDrop);
				Boolean dropPressed = getMinecraftClient().currentScreen.keyPressed(keyDrop.getCode(), 0, 0);
				if(dropPressed || !hasSpace(inv, outStack)){
					ply.dropSelectedItem(true);
				}
				im.clickSlot(csh.syncId, resultSlotIndex, 0, SlotActionType.QUICK_MOVE, ply);
			}
		}
	}

	protected boolean hasSpace(PlayerInventory inv, ItemStack outStack){
		return outStack.isEmpty() || inv.getEmptySlot() >= 0 || inv.getOccupiedSlotWithRoomForStack(outStack) >= 0;
	}
}
