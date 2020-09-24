package eu.gflash.quickcraft.mixin;

import eu.gflash.quickcraft.client.InventoryHelper;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.recipe.Recipe;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeBookResults.class)
public abstract class RecipeClickMixin {
	@Shadow public @Nullable abstract Recipe<?> getLastClickedRecipe();

	@Inject(method = "mouseClicked", at = @At("RETURN"))
	protected void mouseClicked(double mouseX, double mouseY, int button, int areaLeft, int areaTop, int areaWidth, int areaHeight, CallbackInfoReturnable<Boolean> cir){
		InventoryHelper.setLastRecipe(getLastClickedRecipe());
	}
}
