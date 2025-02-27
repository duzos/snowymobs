package dev.duzo.snowmob.api;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeCodec {
	public static final Codec<EntityType> CODEC = ResourceLocation.CODEC.xmap(ForgeRegistries.ENTITY_TYPES::getValue, EntityType::getKey);
}
