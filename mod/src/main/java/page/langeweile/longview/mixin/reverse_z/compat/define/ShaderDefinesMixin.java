/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.reverse_z.compat.define;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.ShaderDefines;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import page.langeweile.longview.impl.LongviewImpl;

@Mixin(ShaderDefines.class)
public abstract class ShaderDefinesMixin {
	@ModifyReturnValue(method = "builder", at = @At("TAIL"))
	private static ShaderDefines.Builder addLongviewDefine(ShaderDefines.Builder original) {
		if (RenderSystem.getDevice().isZZeroToOne()) {
			original.define("Z_ZERO_TO_ONE");
		}

		if (LongviewImpl.isZReversed()) {
			original.define("REVERSE_Z");
		}

		return original;
	}
}
