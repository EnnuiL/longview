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

// This mixin applies only to Distant Horizons 3.0.0.
// Them reimplementing Minecraft 1.16.5's Mat4f class is *horrid*, but we do what we must do.
@Mixin(targets = "com.seibel.distanthorizons.core.util.math.Mat4f")
public abstract class Mat4fMixin extends DhApiMat4f {
	// The "perspective" method is unused, we won't bother with it

	@WrapMethod(method = "setClipPlanes(FF)V")
	public void swapClipPlanes3_0(float nearClip, float farClip, Operation<Object> original) {
		boolean reverseZ = LongviewImpl.isZReversed();
		boolean zZeroToOne = RenderSystem.getDevice().isZZeroToOne();

		if (reverseZ) {
			float buf = nearClip;
			nearClip = farClip;
			farClip = buf;
		}

		if (zZeroToOne) {
			// Used JOML as a reference here
			this.m22 = farClip / (nearClip - farClip);
			this.m23 = farClip * nearClip / (nearClip - farClip);
		} else {
			original.call(nearClip, farClip);
		}
	}
}
