/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.zero_to_one.compat.iris;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import page.langeweile.longview.impl.LongviewImpl;

@Mixin(targets = "net.irisshaders.iris.pipeline.PipelineManager")
public class PipelineManagerMixin {
	@Inject(
		method = "preparePipeline",
		at = @At(
			value = "INVOKE_ASSIGN",
			target = "Lnet/irisshaders/iris/shaderpack/materialmap/WorldRenderingSettings;isReloadRequired()Z"
		)
	)
	private void toggleZZeroToOneOnReload(CallbackInfoReturnable<?> cir) {
		// This is only applicable to the OpenGL backend
		if (RenderSystem.getDevice().getBackendName().equals("OpenGL")) {
			LongviewImpl.toggleZZeroToOne(LongviewImpl.isZReversed());
		}
	}
}
