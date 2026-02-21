package eu.gflash.quickcraft.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import java.util.Arrays;
import net.minecraft.client.Minecraft;

public abstract class InputHelper {
    private InputHelper() {}

    public static boolean isKeyPressed(int ...keyCodes){
        Window window = Minecraft.getInstance().getWindow();
        return Arrays.stream(keyCodes).anyMatch(c -> InputConstants.isKeyDown(window, c));
    }

    public static boolean isAltPressed(){
        return isKeyPressed(InputConstants.KEY_LALT, InputConstants.KEY_RALT);
    }

    public static boolean isCtrlPressed(){
        return isKeyPressed(InputConstants.KEY_LCONTROL, InputConstants.KEY_RCONTROL);
    }
}
