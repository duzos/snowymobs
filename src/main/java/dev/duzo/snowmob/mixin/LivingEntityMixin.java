package dev.duzo.snowmob.mixin;

import dev.duzo.snowmob.api.SnowCollecting;
import dev.duzo.snowmob.api.SnowData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements SnowCollecting {
	@Unique
	private static final EntityDataAccessor<Integer> SNOW_LEVEL = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.INT);

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void snowmob$readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag, CallbackInfo ci) {
		setSnowLevel(tag.getInt("SnowLevel"));
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void snowmob$addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag, CallbackInfo ci) {
		tag.putInt("SnowLevel", getSnowLevel());
	}

	@Inject(method = "defineSynchedData", at = @At("TAIL"))
	public void snowmob$defineSynchedData(CallbackInfo ci) {
		((LivingEntity) (Object) this).getEntityData().define(SNOW_LEVEL, 0);
	}

	@Override
	public int getSnowLevel() {
		return ((LivingEntity) (Object) this).getEntityData().get(SNOW_LEVEL);
	}

	@Override
	public int getMaxSnow() {
		return SnowData.getMaxLevel((LivingEntity) (Object) this);
	}

	@Override
	public boolean setSnowLevel(int level) {
		((LivingEntity) (Object) this).getEntityData().set(SNOW_LEVEL, Mth.clamp(level, 0, getMaxSnow()));

		return getSnowLevel() == level;
	}
}
