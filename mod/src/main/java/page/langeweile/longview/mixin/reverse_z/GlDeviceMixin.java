/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.reverse_z;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import page.langeweile.longview.api.LongviewDevice;

// The most important mixin for the reverse Z depth buffer trick to properly function
@Mixin(targets = "com.mojang.blaze3d.opengl.GlDevice")
public abstract class GlDeviceMixin implements LongviewDevice {
	@Override
	public boolean supportsReverseZ() {
		return true;
	}

	// This tries to only patch post/transparency.fsh so that it accounts for reversed Zs
	// This fixes Improved Transparency
	@ModifyExpressionValue(method = "compileShader", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/preprocessor/GlslPreprocessor;injectDefines(Ljava/lang/String;Lnet/minecraft/client/renderer/ShaderDefines;)Ljava/lang/String;"))
	private String patchTransparencyFragmentShader(String original) {
		if (original.contains("try_insert")) {
			return original.replace("depth_layers[jj] > depth_layers[ii]", "depth_layers[jj] < depth_layers[ii]");
		}

		return original;
	}
}
