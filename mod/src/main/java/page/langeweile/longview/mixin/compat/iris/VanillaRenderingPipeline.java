/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.compat.iris;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import page.langeweile.longview.impl.LongviewImpl;

@Mixin(targets = "net.irisshaders.iris.pipeline.VanillaRenderingPipeline")
public abstract class VanillaRenderingPipeline {
	@Inject(method = "<init>()V", at = @At("HEAD"))
	private static void enableLongview(CallbackInfo ci) {
		LongviewImpl.setIrisLongview(true, true);
	}
}
