/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.reverse_z;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.opengl.GlConst;
import org.lwjgl.opengl.GL11C;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GlConst.class)
public abstract class GlConstMixin {
	@ModifyExpressionValue(
		method = "toGl(Lcom/mojang/blaze3d/platform/DepthTestFunction;)I",
		at = @At(
			value = "CONSTANT",
			args = "intValue=513"
		)
	)
	private static int modifyLessDepthTestFunction(int original) {
		return GL11C.GL_GREATER;
	}

	@ModifyExpressionValue(
		method = "toGl(Lcom/mojang/blaze3d/platform/DepthTestFunction;)I",
		at = @At(
			value = "CONSTANT",
			args = "intValue=516"
		)
	)
	private static int modifyGreaterDepthTestFunction(int original) {
		return GL11C.GL_LESS;
	}

	@ModifyExpressionValue(
		method = "toGl(Lcom/mojang/blaze3d/platform/DepthTestFunction;)I",
		at = @At(
			value = "CONSTANT",
			args = "intValue=515"
		)
	)
	private static int modifyLequalDepthTestFunction(int original) {
		return GL11C.GL_GEQUAL;
	}

	// Technically this could be used but as far as I can tell, this is completely optional and isn't required to make Reverse Z work
	/*
	@ModifyExpressionValue(
			method = "toGlInternalId",
			at = @At(
					value = "CONSTANT",
					args = "intValue=33191"
			)
	)
	private static int modifyDepthComponent(int original) {
		return GL30C.GL_DEPTH_COMPONENT32F;
	}
	 */
}
