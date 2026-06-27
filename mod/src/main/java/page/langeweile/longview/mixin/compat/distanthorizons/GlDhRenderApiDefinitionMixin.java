/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.compat.distanthorizons;

import com.seibel.distanthorizons.core.render.EDhRenderDepth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import page.langeweile.longview.impl.LongviewImpl;

// Trying to apply the equivalent of this mixin to Blaze3D backend will break it since Longview already translates everything to Reverse Z
@Mixin(targets = "com.seibel.distanthorizons.common.render.openGl.GlDhRenderApiDefinition")
public abstract class GlDhRenderApiDefinitionMixin {
	// Longview supports fixing itself dynamically, so do that!
	@Inject(method = "getRenderDepth", at = @At("HEAD"), cancellable = true, require = 0)
	private void modifyRenderDepth(CallbackInfoReturnable<EDhRenderDepth> cir) {
		if (LongviewImpl.isZReversed()) {
			cir.setReturnValue(EDhRenderDepth.REVERSE_Z);
		}
	}
}
