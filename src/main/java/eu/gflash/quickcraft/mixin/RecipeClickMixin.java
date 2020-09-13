package eu.gflash.quickcraft.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.inventory.CraftingInventory;
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
			ScreenHandler csh = ply.currentScreenHandler;
			ClientPlayerInteractionManager im = MinecraftClient.getInstance().interactionManager;
			if(csh instanceof CraftingScreenHandler || csh instanceof PlayerScreenHandler){
				int resultSlotIndex = ((AbstractRecipeScreenHandler<?>) csh).getCraftingResultSlotIndex();
				im.clickSlot(csh.syncId, resultSlotIndex, 0, SlotActionType.QUICK_MOVE, ply);
			}
		}
	}
}
