package eu.gflash.quickcraft.mixin;

import eu.gflash.quickcraft.client.InventoryHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
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
        boolean isControlDown = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow(), InputUtil.GLFW_KEY_LEFT_CONTROL) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow(), InputUtil.GLFW_KEY_RIGHT_CONTROL) ;
		if(isControlDown) {
            InventoryHelper.scheduleCraft();
        }
    }
}
