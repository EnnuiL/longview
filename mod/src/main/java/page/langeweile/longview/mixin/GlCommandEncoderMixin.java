/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.systems.RenderPassBackend;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import page.langeweile.longview.api.LongviewDevice;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Supplier;

@Mixin(targets = "com.mojang.blaze3d.opengl.GlCommandEncoder")
public class GlCommandEncoderMixin implements LongviewDevice {
    @WrapMethod(method = "createRenderPass(Ljava/util/function/Supplier;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalInt;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalDouble;)Lcom/mojang/blaze3d/systems/RenderPassBackend;")
    private RenderPassBackend invertCreateRenderPassDepthBuffer(Supplier<String> label, GpuTextureView colorTexture, OptionalInt clearColor, @Nullable GpuTextureView depthTexture, OptionalDouble clearDepth, Operation<RenderPassBackend> original) {
        return original.call(label, colorTexture, clearColor, depthTexture, clearDepth.isPresent() ? OptionalDouble.of(1.0 - clearDepth.getAsDouble()) : clearDepth);
    }

    @WrapMethod(method = "clearColorAndDepthTextures(Lcom/mojang/blaze3d/textures/GpuTexture;ILcom/mojang/blaze3d/textures/GpuTexture;D)V")
    private void invertClearColorAndDepthTexturesDepthBuffer(GpuTexture colorTexture, int clearColor, GpuTexture depthTexture, double clearDepth, Operation<Void> original) {
        original.call(colorTexture, clearColor, depthTexture, 1.0 - clearDepth);
    }

    @WrapMethod(method = "clearColorAndDepthTextures(Lcom/mojang/blaze3d/textures/GpuTexture;ILcom/mojang/blaze3d/textures/GpuTexture;DIIII)V")
    private void invertClearColorAndDepthTexturesDepthBuffer(GpuTexture colorTexture, int clearColor, GpuTexture depthTexture, double clearDepth, int regionX, int regionY, int regionWidth, int regionHeight, Operation<Void> original) {
        original.call(colorTexture, clearColor, depthTexture, 1.0 - clearDepth, regionX, regionY, regionWidth, regionHeight);
    }

    @WrapMethod(method = "clearDepthTexture")
    private void invertClearColorTextureDepthBuffer(GpuTexture depthTexture, double clearDepth, Operation<Void> original) {
        original.call(depthTexture, 1.0 - clearDepth);
    }

    @ModifyExpressionValue(method = "applyPipelineState", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderPipeline;getDepthBiasConstant()F"))
    private float invertDepthBiasConstant(float original) {
        return -original;
    }

    @ModifyExpressionValue(method = "applyPipelineState", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderPipeline;getDepthBiasScaleFactor()F"))
    private float invertDepthBiasScaleFactor(float original) {
        return -original;
    }
}
