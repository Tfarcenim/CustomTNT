package tfar.customtnt.mixin;

import net.minecraft.client.MouseHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import tfar.customtnt.ClientHooks;

@Mixin(MouseHelper.class)
public class MouseHelperMixin {

	@Inject(at = @At(value = "INVOKE",target = "Lnet/minecraft/client/entity/player/ClientPlayerEntity;rotateTowards(DD)V"), method = "updatePlayerLook",locals = LocalCapture.CAPTURE_FAILHARD,cancellable = true)
	private void init(CallbackInfo ci, double d0, double d1, double d4, double d5, double d2, double d3, int i) {
		if (ClientHooks.updateTNTLook(d2,d3 * i)) {
			ci.cancel();
		}
	}
}
