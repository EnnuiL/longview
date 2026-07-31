/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.compat.iris;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import page.langeweile.longview.impl.LongviewImpl;

@Mixin(targets = "net.irisshaders.iris.pathways.HandRenderer")
public class HandRendererMixin {
	@ModifyArg(
		method = "setupGlState",
		at = @At(
			value = "INVOKE",
			target = "Lorg/joml/Matrix4f;scale(FFF)Lorg/joml/Matrix4f;"
		),
		index = 2
	)
	private float modifyDepth(float original) {
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
