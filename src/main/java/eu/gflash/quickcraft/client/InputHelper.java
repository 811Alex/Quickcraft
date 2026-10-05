package eu.gflash.quickcraft.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import java.util.Arrays;
import net.minecraft.client.Minecraft;

public abstract class InputHelper {
    private InputHelper() {}

    public static boolean isKeyPressed(int ...keyCodes){
        Window window = Minecraft.getInstance().getWindow();
        return Arrays.stream(keyCodes).anyMatch(c -> isKeyDown(window, c));
    }

    private static boolean isKeyDown(Window window, int keyCode) {
        try {
            try {
                return (boolean) InputConstants.class.getMethod("isKeyDown", int.class).invoke(null, keyCode);
            } catch (NoSuchMethodException e) {
                return (boolean) InputConstants.class.getMethod("isKeyDown", Window.class, int.class)
                        .invoke(null, window, keyCode);
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to check whether a key is pressed", e);
        }
    }

    public static boolean isAltPressed(){
        return isKeyPressed(InputConstants.KEY_LALT, InputConstants.KEY_RALT);
    }

    public static boolean isCtrlPressed(){
        return isKeyPressed(InputConstants.KEY_LCONTROL, InputConstants.KEY_RCONTROL);
    }
}
