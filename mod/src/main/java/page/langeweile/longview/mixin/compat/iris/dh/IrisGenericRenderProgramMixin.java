/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.compat.iris.dh;

import org.lwjgl.opengl.GL11C;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import page.langeweile.longview.impl.LongviewImpl;

@Mixin(targets = "net.irisshaders.iris.compat.dh.IrisGenericRenderProgram")
public class IrisGenericRenderProgramMixin {
	@ModifyArg(
		method = "fillIndirectUniformData",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/blaze3d/opengl/GlStateManager;_depthFunc(I)V"
		)
	)
	private int modifyDepthFunc(int original) {
		if (LongviewImpl.isZReversed()) {
			return GL11C.GL_GEQUAL;
		} else {
			return original;
		}
	}
}
