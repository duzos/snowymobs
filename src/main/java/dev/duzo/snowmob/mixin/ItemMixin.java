package dev.duzo.snowmob.mixin;

import dev.duzo.snowmob.api.SnowCollecting;
import dev.duzo.snowmob.api.SnowData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
	@Inject(method = "interactLivingEntity", at = @At("TAIL"), cancellable = true)
	private void snowmob$interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand p_41401_, CallbackInfoReturnable<InteractionResult> cir) {
		// check if this is snow layer
		if (!(stack.is(Blocks.SNOW.asItem())) && !(stack.is(Items.SNOWBALL)) && !(stack.is(SnowData.ADDS_SNOW))) return;

		SnowCollecting snow = (SnowCollecting) entity;
		if (snow.addSnow(1)) {
			entity.playSound(SoundEvents.SNOW_PLACE);
			stack.shrink(1);
			cir.setReturnValue(InteractionResult.SUCCESS);
		}
	}
}
