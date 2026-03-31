/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.reverse_z;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.platform.CompareOp;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import page.langeweile.longview.impl.LongviewImpl;

@Mixin(DepthStencilState.class)
public abstract class DepthStencilStateMixin {
	@ModifyReturnValue(method = "depthTest", at = @At("TAIL"))
	private CompareOp modifyDepthTest(CompareOp original) {
		return LongviewImpl.isZReversed()
			? switch (original) {
				case LESS_THAN -> CompareOp.GREATER_THAN;
				case LESS_THAN_OR_EQUAL -> CompareOp.GREATER_THAN_OR_EQUAL;
				case GREATER_THAN_OR_EQUAL -> CompareOp.LESS_THAN_OR_EQUAL;
				case GREATER_THAN -> CompareOp.LESS_THAN;
				default -> original;
			}
			: original;
	}

	@ModifyReturnValue(method = "depthBiasConstant", at = @At("TAIL"))
	private float modifyDepthBiasConstant(float original) {
		return LongviewImpl.isZReversed()
			? -original
			: original;
	}

	@ModifyReturnValue(method = "depthBiasScaleFactor", at = @At("TAIL"))
	private float modifyDepthBiasScaleFactor(float original) {
		return LongviewImpl.isZReversed()
			? -original
			: original;
	}
}
