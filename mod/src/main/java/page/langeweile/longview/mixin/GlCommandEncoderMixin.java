/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import page.langeweile.longview.api.LongviewCommandEncoder;

@Mixin(targets = "com.mojang.blaze3d.opengl.GlCommandEncoder")
public class GlCommandEncoderMixin implements LongviewCommandEncoder {
    @ModifyExpressionValue(method = "applyPipelineState", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderPipeline;getDepthBiasConstant()F"))
    private float invertDepthBiasConstant(float original) {
        return -original;
    }

    @ModifyExpressionValue(method = "applyPipelineState", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderPipeline;getDepthBiasScaleFactor()F"))
    private float invertDepthBiasScaleFactor(float original) {
        return -original;
    }

    @Override
    public boolean supportsLongview() {
        return true;
    }
}
