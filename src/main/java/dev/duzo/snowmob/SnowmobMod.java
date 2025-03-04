package dev.duzo.snowmob;

import com.mojang.logging.LogUtils;
import dev.duzo.snowmob.api.SnowData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataPackRegistryEvent;
import org.slf4j.Logger;

@Mod(SnowmobMod.MODID)
public class SnowmobMod {
	public static final String MODID = "snowmob";
	private static final Logger LOGGER = LogUtils.getLogger();

	public SnowmobMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		MinecraftForge.EVENT_BUS.register(this);

		bus.addListener((DataPackRegistryEvent.NewRegistry event) -> {
			event.dataPackRegistry(SnowData.REGISTRY_KEY, SnowData.CODEC);
		});
	}

	private void commonSetup(final FMLCommonSetupEvent event) {

	}

	// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {

		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {

		}
	}
}
