package dev.duzo.snowmob.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.duzo.snowmob.api.SnowData;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
	@Shadow
	protected M model;
	@Unique
	private boolean snowmob$renderingSnow = false;

	@Shadow
	public abstract void render(T p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_);

	@Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("TAIL"))
	public void snowmob$render(T p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_, CallbackInfo ci) {
		if (!(snowmob$renderingSnow)) {
			snowmob$renderingSnow = true;
			this.render(p_114485_, p_114486_, p_114487_, p_114488_, p_114489_, p_114490_);
		}

		snowmob$renderingSnow = false;
	}

	@Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
	public void snowmob$getRenderType(T entity, boolean p_115323_, boolean p_115324_, boolean p_115325_, CallbackInfoReturnable<RenderType> cir) {
		if (!snowmob$renderingSnow) return;

		ResourceLocation texture = SnowData.texture(entity).orElse(null);
		if (texture == null) return;

		cir.setReturnValue(this.model.renderType(texture));
	}
}
