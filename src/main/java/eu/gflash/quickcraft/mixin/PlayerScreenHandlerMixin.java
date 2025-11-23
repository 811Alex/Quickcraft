package eu.gflash.quickcraft.mixin;

import eu.gflash.quickcraft.client.InputHelper;
import eu.gflash.quickcraft.client.InventoryHelper;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.PlayerScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerScreenHandler.class)
public class PlayerScreenHandlerMixin {

    @Inject(method = "onContentChanged", at = @At("RETURN"))
    private void onContentChanged(Inventory inventory, CallbackInfo ci){
		if(InputHelper.isCtrlPressed()) {
            InventoryHelper.scheduleCraft();
        }
    }
}
