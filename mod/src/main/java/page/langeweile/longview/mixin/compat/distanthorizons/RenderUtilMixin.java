/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.compat.distanthorizons;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.systems.RenderSystem;
import com.seibel.distanthorizons.api.objects.math.DhApiMat4f;
import org.spongepowered.asm.mixin.Mixin;
import page.langeweile.longview.impl.LongviewImpl;

@Mixin(targets = "com.seibel.distanthorizons.core.util.RenderUtil")
public class RenderUtilMixin {
	@WrapMethod(method = "setClipPlanes(Lcom/seibel/distanthorizons/api/objects/math/DhApiMat4f;FFZ)V", require = 0)
	private static void swapClipPlanes3_1(DhApiMat4f matrix, float nearClip, float farClip, boolean zZeroToOne, Operation<Object> original) {
		if (LongviewImpl.isZReversed()) {
			float buf = nearClip;
			nearClip = farClip;
			farClip = buf;
		}

		original.call(matrix, nearClip, farClip, RenderSystem.getDevice().isZZeroToOne());
	}
}
