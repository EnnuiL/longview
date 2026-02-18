/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.reverse_z;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import page.langeweile.longview.api.LongviewDevice;

import java.util.OptionalDouble;

@Mixin(targets = "com.mojang.blaze3d.opengl.GlCommandEncoder")
public abstract class GlCommandEncoderMixin implements LongviewDevice {
    @ModifyVariable(
            method = "createRenderPass(Ljava/util/function/Supplier;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalInt;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalDouble;)Lcom/mojang/blaze3d/systems/RenderPassBackend;",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0
    )
    private OptionalDouble invertCreateRenderPassDepthBuffer(OptionalDouble original) {
        return original.isPresent() ? OptionalDouble.of(1.0 - original.getAsDouble()) : original;
    }

    @ModifyVariable(
            method = {
                    "clearColorAndDepthTextures(Lcom/mojang/blaze3d/textures/GpuTexture;ILcom/mojang/blaze3d/textures/GpuTexture;D)V",
                    "clearColorAndDepthTextures(Lcom/mojang/blaze3d/textures/GpuTexture;ILcom/mojang/blaze3d/textures/GpuTexture;DIIII)V",
                    "clearDepthTexture"
            },
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0
    )
    private double invertCreatDepthTextureDepthBuffer(double original) {
        return 1.0 - original;
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
