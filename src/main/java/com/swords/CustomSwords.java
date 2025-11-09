package com.swords;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class CustomSwords implements ModInitializer {
	public static final String MOD_ID = "custom-swords";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static class Items {
		// Custom Item Group
		public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(CustomSwords.MOD_ID, "item_group"));
		public static final ItemGroup CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
				.icon(() -> new ItemStack(Items.FIRE_SWORD))
				.displayName(Text.translatable("itemGroup.CustomSwords"))
				.build();

		// This is for registering the Items (Have to do it only once)
		public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings){
			// Create the item key.
			RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(CustomSwords.MOD_ID, name));

			// Create the item instance.
			Item item = itemFactory.apply(settings.registryKey(itemKey));

			// Register the item.
			Registry.register(Registries.ITEM, itemKey, item);

			return item;
		}

		// Create Fire Sword
		public static final Item FIRE_SWORD = register(
				"fire_sword",
				settings -> new SwordItem(ToolMaterial.DIAMOND, 300, -2.4F, settings),
				new Item.Settings()
		);

		public static void initialize() {
			Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

			ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
				itemGroup.add(FIRE_SWORD);

			});
		}
	}
	@Override
	public void onInitialize() {
		Items.initialize();
		LOGGER.info("Custom Swords mod initialized!");
	}
}