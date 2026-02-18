/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.shaders.GpuDebugOptions;
import com.mojang.blaze3d.shaders.ShaderSource;
import org.lwjgl.opengl.GL45;
import org.lwjgl.opengl.GLCapabilities;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

// The most important mixin for the reverse Z depth buffer trick to properly function
@Mixin(targets = "com.mojang.blaze3d.opengl.GlDevice")
public class GlDeviceMixin {
	@Shadow
	@Final
	private static Logger LOGGER;

	@Shadow
	@Final
	private Set<String> enabledExtensions;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void useArbClipControl(long windowHandle, ShaderSource defaultShaderSource, GpuDebugOptions debugOptions, CallbackInfo ci, @Local GLCapabilities capabilities) {
		if (capabilities.GL_ARB_clip_control) {
			GL45.glClipControl(GL45.GL_LOWER_LEFT, GL45.GL_ZERO_TO_ONE);
			this.enabledExtensions.add("GL_ARB_clip_control");
		} else {
			LOGGER.warn("[Longview] This device does not support the GL_ARB_clip_control extension! The Z depth buffers remain reversed but no improvements will be visible.");
		}
	}

	@ModifyReturnValue(method = "isZZeroToOne", at = @At("TAIL"))
	private boolean enableZZeroToOne(boolean original) {
		return original || this.enabledExtensions.contains("GL_ARB_clip_control");
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
