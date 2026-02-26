/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.reverse_z;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.platform.CompareOp;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DepthStencilState.class)
public abstract class DepthStencilStateMixin {
	@Inject(method = "<init>(Lcom/mojang/blaze3d/platform/CompareOp;ZFF)V", at = @At("HEAD"))
	private static void modifyCompareOp(
		CallbackInfo ci,
		@Local(argsOnly = true) LocalRef<CompareOp> depthTest,
		@Local(argsOnly = true, ordinal = 0) LocalFloatRef depthBiasScaleFactor,
		@Local(argsOnly = true, ordinal = 1) LocalFloatRef depthBiasConstant
	) {
		depthTest.set(
			switch (depthTest.get()) {
				case LESS_THAN -> CompareOp.GREATER_THAN;
				case LESS_THAN_OR_EQUAL -> CompareOp.GREATER_THAN_OR_EQUAL;
				case GREATER_THAN_OR_EQUAL -> CompareOp.LESS_THAN_OR_EQUAL;
				case GREATER_THAN -> CompareOp.LESS_THAN;
				default -> depthTest.get();
			}
		);
		// The following methods ensure that things like text on glowing signs won't be ordered the wrong way
		depthBiasScaleFactor.set(-depthBiasScaleFactor.get());
		depthBiasConstant.set(-depthBiasConstant.get());
	}
}
