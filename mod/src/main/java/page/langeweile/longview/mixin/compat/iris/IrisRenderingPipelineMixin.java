/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.compat.iris;

import com.mojang.blaze3d.systems.RenderSystem;
import net.irisshaders.iris.shaderpack.programs.ProgramSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import page.langeweile.longview.impl.LongviewImpl;
import page.langeweile.longview.impl.compat.LongviewPackDirectives;

@Mixin(targets = "net.irisshaders.iris.pipeline.IrisRenderingPipeline")
public class IrisRenderingPipelineMixin {
	@Inject(
		method = "<init>(Lnet/irisshaders/iris/shaderpack/programs/ProgramSet;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/irisshaders/iris/shaderpack/properties/PackDirectives;underwaterOverlay()Z"
		)
	)
	private static void applyShaderPackOverrides(ProgramSet programSet, CallbackInfo ci) {
		LongviewImpl.setIrisLongview(
			((LongviewPackDirectives) programSet.getPackDirectives()).enableReverseZ(),
			((LongviewPackDirectives) programSet.getPackDirectives()).enableZZeroToOne()
		);

		// This is only applicable to the OpenGL backend
		if (RenderSystem.tryGetDevice() != null && RenderSystem.tryGetDevice().getBackendName().equals("OpenGL")) {
			LongviewImpl.toggleZZeroToOne(LongviewImpl.isGlZZeroToOne());
		}
	}
}
