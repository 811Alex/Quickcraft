package eu.gflash.quickcraft.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;

import java.util.Arrays;

public abstract class InputHelper {
    private InputHelper() {}

    public static boolean isKeyPressed(int ...keyCodes){
        Window window = MinecraftClient.getInstance().getWindow();
        return Arrays.stream(keyCodes).anyMatch(c -> InputUtil.isKeyPressed(window, c));
    }

    public static boolean isAltPressed(){
        return isKeyPressed(InputUtil.GLFW_KEY_LEFT_ALT, InputUtil.GLFW_KEY_RIGHT_ALT);
    }

    public static boolean isCtrlPressed(){
        return isKeyPressed(InputUtil.GLFW_KEY_LEFT_CONTROL, InputUtil.GLFW_KEY_RIGHT_CONTROL);
    }
}
