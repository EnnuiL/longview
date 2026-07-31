package page.langeweile.longview.mixin.debug;

import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import page.langeweile.longview.impl.debug.DebugEntryLongview;

@Mixin(DebugScreenEntries.class)
public abstract class DebugScreenEntriesMixin {
	@Shadow
	private static Identifier register(final Identifier identifier, final DebugScreenEntry entry) {
		return null;
	}

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void registerLongviewEntry(CallbackInfo ci) {
		register(DebugEntryLongview.ENTRY, new DebugEntryLongview());
	}
}
