/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.compat.iris;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import page.langeweile.longview.impl.LongviewImpl;

import java.util.ArrayList;
import java.util.List;

@Mixin(targets = "net.irisshaders.iris.gl.shader.StandardMacros")
public abstract class StandardMacrosMixin {
	@Shadow
	private static void define(List<Object> defines, String key) {}

	@Inject(
		method = "createStandardEnvironmentDefines",
		at = @At(
			value = "INVOKE",
			target = "Lnet/irisshaders/iris/gl/shader/StandardMacros;define(Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V",
			ordinal = 0
		)
	)
	private static void injectLongviewDefine(CallbackInfoReturnable<ImmutableList<Object>> cir, @Local(index = 0) ArrayList<Object> standardDefines) {
		if (LongviewImpl.isZReversed()) {
			define(standardDefines, "LONGVIEW_REVERSE_Z");
		}

		if (LongviewImpl.isGlZZeroToOne() && (RenderSystem.tryGetDevice() != null && RenderSystem.tryGetDevice().isZZeroToOne())) {
			define(standardDefines, "LONGVIEW_Z_ZERO_TO_ONE");
		}
	}

	@ModifyArg(
		method = "createStandardEnvironmentDefines",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Float;toString(F)Ljava/lang/String;"
		),
		index = 0
	)
	private static float modifyHandDepth(float original) {
		boolean reverseZ = LongviewImpl.isZReversed();
		boolean zZeroToOne = LongviewImpl.isGlZZeroToOne() && (RenderSystem.tryGetDevice() != null && RenderSystem.tryGetDevice().isZZeroToOne());
		if (reverseZ && zZeroToOne) {
			return 4.0F;
		} else if (reverseZ) {
			return 8.0F;
		} else if (zZeroToOne) {
			return 0.0625F;
		} else {
			return original;
		}
	}
}
