package page.langeweile.longview.mixin.debug;

import net.minecraft.client.gui.components.debug.DebugScreenEntryList;
import net.minecraft.client.gui.components.debug.DebugScreenEntryStatus;
import net.minecraft.client.gui.components.debug.DebugScreenProfile;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import page.langeweile.longview.impl.debug.DebugEntryLongview;

@Mixin(DebugScreenEntryList.class)
public abstract class DebugScreenEntryListMixin {
	@Shadow
	public abstract void setStatus(Identifier location, DebugScreenEntryStatus status);

	@Inject(method = "resetToProfile", at = @At("TAIL"))
	private void includeLongview(DebugScreenProfile profile, CallbackInfo ci) {
		if (profile.equals(DebugScreenProfile.DEFAULT)) {
			setStatus(DebugEntryLongview.ENTRY, DebugScreenEntryStatus.IN_OVERLAY);
		}
	}
}
