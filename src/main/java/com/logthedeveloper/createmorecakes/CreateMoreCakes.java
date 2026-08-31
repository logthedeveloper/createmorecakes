package com.logthedeveloper.createmorecakes;

import org.slf4j.Logger;

import com.logthedeveloper.createmorecakes.item.BlazeMilkCakeItem;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(CreateMoreCakes.MODID)
public class CreateMoreCakes {
    public static final String MODID = "createmorecakes";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredItem<BlazeMilkCakeItem> BLAZE_MILK_CAKE = ITEMS.registerItem("blaze_milk_cake",
            BlazeMilkCakeItem::new,
            new Item.Properties().food(
                    new FoodProperties.Builder()
                            .nutrition(6)
                            .saturationModifier(0.8f)
                            .build()
            ));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATE_MORE_CAKES_TAB = CREATIVE_MODE_TABS.register("createmorecakestab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.createmorecakes"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> BLAZE_MILK_CAKE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(BLAZE_MILK_CAKE.get());
            }).build());

    public CreateMoreCakes(IEventBus modEventBus, ModContainer modContainer) {
        NeoForgeMod.enableMilkFluid();

        modEventBus.addListener(this::commonSetup);

        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // No vanilla tab additions needed right now — your item lives in its own tab above.
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}