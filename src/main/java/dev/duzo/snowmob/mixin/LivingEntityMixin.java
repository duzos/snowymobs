package dev.duzo.snowmob.mixin;

import dev.duzo.snowmob.api.SnowCollecting;
import dev.duzo.snowmob.api.SnowData;
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
	private int snowmob$snowLevel = 0;

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void snowmob$readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag, CallbackInfo ci) {
		snowmob$snowLevel = tag.getInt("SnowLevel");
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void snowmob$addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag, CallbackInfo ci) {
		tag.putInt("SnowLevel", snowmob$snowLevel);
	}


	@Override
	public int getSnowLevel() {
		return snowmob$snowLevel;
	}

	@Override
	public int getMaxSnow() {
		return SnowData.getMaxLevel((LivingEntity) (Object) this);
	}

	@Override
	public boolean setSnowLevel(int level) {
		snowmob$snowLevel = Mth.clamp(level, 0, getMaxSnow());

		return snowmob$snowLevel == level;
	}
}
