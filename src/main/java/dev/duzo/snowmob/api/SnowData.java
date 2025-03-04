package dev.duzo.snowmob.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.duzo.snowmob.SnowmobMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.List;
import java.util.Optional;

public record SnowData(EntityType<?> type, List<ResourceLocation> textures) {
	public static final Codec<SnowData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			EntityTypeCodec.CODEC.fieldOf("type").forGetter(SnowData::type),
			ResourceLocation.CODEC.listOf().fieldOf("textures").forGetter(SnowData::textures)
	).apply(instance, SnowData::new));

	public static final ResourceKey<Registry<SnowData>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(SnowmobMod.MODID, "snow_data"));
	public static final TagKey<Item> ADDS_SNOW = TagKey.create(Registries.ITEM, new ResourceLocation(SnowmobMod.MODID, "adds_snow"));

	public static Registry<SnowData> getRegistry() {
		return ServerLifecycleHooks.getCurrentServer().registryAccess().registry(REGISTRY_KEY).orElse(null);
	}

	public static Optional<SnowData> get(EntityType<?> type) {
		Registry<SnowData> registry = getRegistry();
		if (registry == null) {
			return Optional.empty();
		}

		for (SnowData data : registry) {
			if (data.type() == type) {
				return Optional.of(data);
			}
		}

		return Optional.empty();
	}

	public static Optional<List<ResourceLocation>> textures(LivingEntity entity) {
		Registry<SnowData> registry = getRegistry();
		if (registry == null) {
			return Optional.empty();
		}

		SnowData data = get(entity.getType()).orElse(null);
		if (data == null) {
			return Optional.empty();
		}

		return Optional.of(data.textures());
	}

	public static Optional<ResourceLocation> texture(LivingEntity entity) {
		Registry<SnowData> registry = getRegistry();
		if (registry == null) {
			return Optional.empty();
		}

		SnowData data = get(entity.getType()).orElse(null);
		if (data == null) {
			return Optional.empty();
		}

		int level = getLevel(entity) - 1;
		if (level < 0 || level >= data.textures().size()) {
			return Optional.empty();
		}

		return Optional.of(data.textures().get(level));
	}

	public static int getLevel(LivingEntity entity) {
		return ((SnowCollecting) entity).getSnowLevel();
	}

	public static int getMaxLevel(LivingEntity entity) {
		Registry<SnowData> registry = getRegistry();
		if (registry == null) {
			return 0;
		}

		SnowData data = get(entity.getType()).orElse(null);
		if (data == null) {
			return 0;
		}

		return data.textures().size();
	}
}
