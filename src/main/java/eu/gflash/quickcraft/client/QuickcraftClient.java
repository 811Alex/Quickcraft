package eu.gflash.quickcraft.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class QuickcraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		InventoryHelper.init();
		System.out.println("Quickcraft loaded!");
	}
}
