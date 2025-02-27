package dev.duzo.snowmob.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public record SnowData(EntityType<?> type, ResourceLocation texture) {
	public static final Codec<SnowData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			EntityTypeCodec.CODEC.fieldOf("type").forGetter(SnowData::type),
			ResourceLocation.CODEC.fieldOf("texture").forGetter(SnowData::texture)
	).apply(instance, SnowData::new));
}
