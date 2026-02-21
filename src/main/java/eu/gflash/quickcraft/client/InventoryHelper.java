package eu.gflash.quickcraft.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AbstractCraftingMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;

public abstract class InventoryHelper {
	private static boolean craftScheduled = false;

	private InventoryHelper() {}

	public static void scheduleCraft(){	// craft on the next client tick
		craftScheduled = true;
	}

	public static void init(){	// register craft on tick
		ClientTickEvents.START_CLIENT_TICK.register((Minecraft minecraftClient)->{
			if(craftScheduled){
				craftScheduled = false;
				InventoryHelper.craft();
			}
		});
	}

	public static void craft(){	// craft the item on the crafting table
		Minecraft client = Minecraft.getInstance();
		LocalPlayer ply = client.player;
		MultiPlayerGameMode im = client.gameMode;
		if(im == null || ply == null) return;
		Inventory inv = ply.getInventory();
		AbstractCraftingMenu rsh = getCraftingScreenHandler();
		if(rsh != null){
			int resultSlotIndex = rsh.getResultSlot().getContainerSlot();
			ItemStack outStack = getResultStack();
			if(InputHelper.isAltPressed() || (outStack != null && !hasSpace(inv, outStack))){
				ply.drop(true);
			}
			im.handleInventoryMouseClick(rsh.containerId, resultSlotIndex, 0, ClickType.QUICK_MOVE, ply);
		}
	}

	public static ItemStack getResultStack(){ // crafting table result
		AbstractCraftingMenu rsh = getCraftingScreenHandler();
		if(rsh == null) return null;
		int resultSlotIndex = rsh.getResultSlot().getContainerSlot();
		return rsh.slots.get(resultSlotIndex).getItem();
	}

	public static AbstractCraftingMenu getCraftingScreenHandler(){	// get crafting area screen handler (table/player)
		LocalPlayer ply = Minecraft.getInstance().player;
		if(ply == null) return null;
		AbstractContainerMenu csh = ply.containerMenu;
		if(csh instanceof CraftingMenu || csh instanceof InventoryMenu)
			return ((AbstractCraftingMenu) csh);
		return null;
	}

	protected static boolean hasSpace(Inventory inv, ItemStack outStack){	// player inventory has space for items
		return outStack.isEmpty() || inv.getFreeSlot() >= 0 || inv.getSlotWithRemainingSpace(outStack) >= 0;
	}
}
