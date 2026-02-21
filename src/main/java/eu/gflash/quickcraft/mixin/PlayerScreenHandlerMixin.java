package eu.gflash.quickcraft.mixin;

import eu.gflash.quickcraft.client.InputHelper;
import eu.gflash.quickcraft.client.InventoryHelper;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryMenu.class)
public class PlayerScreenHandlerMixin {

    @Inject(method = "slotsChanged", at = @At("RETURN"))
    private void onContentChanged(Container inventory, CallbackInfo ci){
		if(InputHelper.isCtrlPressed()) {
            InventoryHelper.scheduleCraft();
        }
    }
}
