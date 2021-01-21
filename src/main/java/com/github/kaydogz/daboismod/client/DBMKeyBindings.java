package com.github.kaydogz.daboismod.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class DBMKeyBindings {
	
	public static KeyBinding quest_menu;
	public static KeyBinding activate_magnetism;
	public static KeyBinding activate_crown;
	
	/**
	 * Registers key bindings.
	 */
	public static void registerKeyBindings() {
		DBMKeyBindings.quest_menu = registerKeyBinding("key.daboismod.questMenu", GLFW.GLFW_KEY_Z);
		DBMKeyBindings.activate_magnetism = registerKeyBinding("key.daboismod.magnetism", GLFW.GLFW_KEY_R);
		DBMKeyBindings.activate_crown = registerKeyBinding("key.daboismod.crown", GLFW.GLFW_KEY_V);
	}

	private static KeyBinding registerKeyBinding(final String translationKeyIn, final int keyCodeIn) {
		final KeyBinding keyBinding = new KeyBinding(translationKeyIn, keyCodeIn, "key.daboismod.categories.daBois");
		ClientRegistry.registerKeyBinding(keyBinding);
		return keyBinding;
	}
}
