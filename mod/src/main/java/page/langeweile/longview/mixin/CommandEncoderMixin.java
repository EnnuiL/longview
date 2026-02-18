/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.CommandEncoderBackend;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import page.langeweile.longview.api.LongviewCommandEncoder;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Supplier;

@Mixin(CommandEncoder.class)
public class CommandEncoderMixin {
	@Shadow
	@Final
	private CommandEncoderBackend backend;

	@WrapMethod(method = "createRenderPass(Ljava/util/function/Supplier;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalInt;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalDouble;)Lcom/mojang/blaze3d/systems/RenderPass;")
	private RenderPass invertCreateRenderPassDepthBuffer(Supplier<String> label, GpuTextureView colorTexture, OptionalInt clearColor, @Nullable GpuTextureView depthTexture, OptionalDouble clearDepth, Operation<RenderPass> original) {
		if (((LongviewCommandEncoder) this.backend).supportsLongview() && clearDepth.isPresent()) {
			return original.call(label, colorTexture, clearColor, depthTexture, OptionalDouble.of(1.0 - clearDepth.getAsDouble()));
		} else {
			return original.call(label, colorTexture, clearColor, depthTexture, clearDepth);
		}
	}

	@WrapMethod(method = "clearColorAndDepthTextures(Lcom/mojang/blaze3d/textures/GpuTexture;ILcom/mojang/blaze3d/textures/GpuTexture;D)V")
	private void invertClearColorAndDepthTexturesDepthBuffer(GpuTexture colorTexture, int clearColor, GpuTexture depthTexture, double clearDepth, Operation<Void> original) {
		if (((LongviewCommandEncoder) this.backend).supportsLongview()) {
			original.call(colorTexture, clearColor, depthTexture, 1.0 - clearDepth);
		} else {
			original.call(colorTexture, clearColor, depthTexture, clearDepth);
		}
	}

	@WrapMethod(method = "clearColorAndDepthTextures(Lcom/mojang/blaze3d/textures/GpuTexture;ILcom/mojang/blaze3d/textures/GpuTexture;DIIII)V")
	private void invertClearColorAndDepthTexturesDepthBuffer(GpuTexture colorTexture, int clearColor, GpuTexture depthTexture, double clearDepth, int regionX, int regionY, int regionWidth, int regionHeight, Operation<Void> original) {
		if (((LongviewCommandEncoder) this.backend).supportsLongview()) {
			original.call(colorTexture, clearColor, depthTexture, 1.0 - clearDepth, regionX, regionY, regionWidth, regionHeight);
		} else {
			original.call(colorTexture, clearColor, depthTexture, clearDepth, regionX, regionY, regionWidth, regionHeight);
		}
	}

	@WrapMethod(method = "clearDepthTexture")
	private void invertClearColorTextureDepthBuffer(GpuTexture depthTexture, double clearDepth, Operation<Void> original) {
		if (((LongviewCommandEncoder) this.backend).supportsLongview()) {
			original.call(depthTexture, 1.0 - clearDepth);
		} else {
			original.call(depthTexture, clearDepth);
		}
	}
}
