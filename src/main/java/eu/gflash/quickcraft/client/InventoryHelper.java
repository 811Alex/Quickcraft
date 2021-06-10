package eu.gflash.quickcraft.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

public abstract class InventoryHelper {
	private static boolean craftScheduled = false;

	public static void scheduleCraft(){	// craft on the next client tick
		craftScheduled = true;
	}

	public static void init(){	// register craft on tick
		ClientTickEvents.START_CLIENT_TICK.register((MinecraftClient minecraftClient)->{
			if(craftScheduled){
				craftScheduled = false;
				InventoryHelper.craft();
			}
		});
	}

	public static void craft(){	// craft the item on the crafting table
		MinecraftClient client = MinecraftClient.getInstance();
		ClientPlayerEntity ply = client.player;
		ClientPlayerInteractionManager im = client.interactionManager;
		if(im == null || ply == null) return;
		PlayerInventory inv = ply.getInventory();
		AbstractRecipeScreenHandler<?> rsh = getRecipeScreenHandler();
		if(rsh != null){
			int resultSlotIndex = rsh.getCraftingResultSlotIndex();
			ItemStack outStack = getResultStack();
			if(Screen.hasAltDown() || (outStack != null && !hasSpace(inv, outStack))){
				ply.dropSelectedItem(true);
			}
			im.clickSlot(rsh.syncId, resultSlotIndex, 0, SlotActionType.QUICK_MOVE, ply);
		}
	}

	public static ItemStack getResultStack(){ // crafting table result
		AbstractRecipeScreenHandler<?> rsh = getRecipeScreenHandler();
		if(rsh == null) return null;
		int resultSlotIndex = rsh.getCraftingResultSlotIndex();
		return rsh.slots.get(resultSlotIndex).getStack();
	}

	public static AbstractRecipeScreenHandler<?> getRecipeScreenHandler(){	// get crafting area screen handler (table/player)
		ClientPlayerEntity ply = MinecraftClient.getInstance().player;
		if(ply == null) return null;
		ScreenHandler csh = ply.currentScreenHandler;
		if(csh instanceof CraftingScreenHandler || csh instanceof PlayerScreenHandler)
			return ((AbstractRecipeScreenHandler<?>) csh);
		return null;
	}

	protected static boolean hasSpace(PlayerInventory inv, ItemStack outStack){	// player inventory has space for items
		return outStack.isEmpty() || inv.getEmptySlot() >= 0 || inv.getOccupiedSlotWithRoomForStack(outStack) >= 0;
	}
}
