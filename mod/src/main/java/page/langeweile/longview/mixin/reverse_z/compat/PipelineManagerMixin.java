/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.reverse_z.compat;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL45;
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
		),
		cancellable = true
	)
	private void a(CallbackInfoReturnable<?> cir) {
		if (LongviewImpl.isZZeroToOne()) {
			GL45.glClipControl(GL45.GL_LOWER_LEFT, GL45.GL_ZERO_TO_ONE);
		} else {
			GL45.glClipControl(GL45.GL_LOWER_LEFT, GL45.GL_NEGATIVE_ONE_TO_ONE);
		}

		var target = Minecraft.getInstance().getMainRenderTarget();
		target.resize(target.width, target.height);
		System.out.println(LongviewImpl.isZZeroToOne());
	}
}
