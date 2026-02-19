/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.reverse_z.compat.cinnabar;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;

@Mixin(targets = "graphics.cinnabar.core.hg3d.Hg3DRenderPipeline")
public class HgRenderPipelineMixin {
    @ModifyExpressionValue(
            method = "lambda$new$1(Lcom/mojang/blaze3d/shaders/ShaderSource;Lgraphics/cinnabar/core/hg3d/Hg3DRenderPipeline$ShaderSourceCacheKey;)Ljava/lang/String;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/shaders/ShaderSource;get(Lnet/minecraft/resources/Identifier;Lcom/mojang/blaze3d/shaders/ShaderType;)Ljava/lang/String;"
            )
    )
    private static String patchShader(String original) {
        if (original.contains("try_insert")) {
            return original.replace("depth_layers[jj] > depth_layers[ii]", "depth_layers[jj] < depth_layers[ii]");
        }

        return original;
    }

    @ModifyExpressionValue(
            method = "<init>(Lgraphics/cinnabar/core/hg3d/Hg3DGpuDevice;Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lcom/mojang/blaze3d/shaders/ShaderSource;)V",
            at = @At(
                    value = "FIELD",
                    target = "Lgraphics/cinnabar/api/hg/enums/HgCompareOp;LESS_OR_EQUAL:Lgraphics/cinnabar/api/hg/enums/HgCompareOp;",
                    opcode = Opcodes.GETSTATIC
            )
    )
    private @Coerce Object modifyLequal(@Coerce Object original) throws ClassNotFoundException {
        return Class.forName("graphics.cinnabar.api.hg.enums.HgCompareOp").getEnumConstants()[6];
    }

    @ModifyExpressionValue(
            method = "<init>(Lgraphics/cinnabar/core/hg3d/Hg3DGpuDevice;Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lcom/mojang/blaze3d/shaders/ShaderSource;)V",
            at = @At(
                    value = "FIELD",
                    target = "Lgraphics/cinnabar/api/hg/enums/HgCompareOp;LESS:Lgraphics/cinnabar/api/hg/enums/HgCompareOp;",
                    opcode = Opcodes.GETSTATIC
            )
    )
    private @Coerce Object modifyLess(@Coerce Object original) throws ClassNotFoundException {
        return Class.forName("graphics.cinnabar.api.hg.enums.HgCompareOp").getEnumConstants()[4];
    }

    @ModifyExpressionValue(
            method = "<init>(Lgraphics/cinnabar/core/hg3d/Hg3DGpuDevice;Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lcom/mojang/blaze3d/shaders/ShaderSource;)V",
            at = @At(
                    value = "FIELD",
                    target = "Lgraphics/cinnabar/api/hg/enums/HgCompareOp;LESS_OR_EQUAL:Lgraphics/cinnabar/api/hg/enums/HgCompareOp;",
                    opcode = Opcodes.GETSTATIC
            )
    )
    private @Coerce Object modifyGreater(@Coerce Object original) throws ClassNotFoundException {
        return Class.forName("graphics.cinnabar.api.hg.enums.HgCompareOp").getEnumConstants()[1];
    }

    @ModifyExpressionValue(
            method = "<init>(Lgraphics/cinnabar/core/hg3d/Hg3DGpuDevice;Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lcom/mojang/blaze3d/shaders/ShaderSource;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/pipeline/RenderPipeline;getDepthBiasConstant()F"
            )
    )
    private float invertDepthBiasConstant(float original) {
        return -original;
    }

    @ModifyExpressionValue(
            method = "<init>(Lgraphics/cinnabar/core/hg3d/Hg3DGpuDevice;Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lcom/mojang/blaze3d/shaders/ShaderSource;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/pipeline/RenderPipeline;getDepthBiasScaleFactor()F"
            )
    )
    private float invertDepthBiasScaleFactor(float original) {
        return -original;
    }
}
