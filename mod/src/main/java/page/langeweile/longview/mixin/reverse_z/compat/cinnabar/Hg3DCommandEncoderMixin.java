package page.langeweile.longview.mixin.reverse_z.compat.cinnabar;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.OptionalDouble;

@Mixin(targets = "graphics.cinnabar.core.hg3d.Hg3DCommandEncoder")
public class Hg3DCommandEncoderMixin {
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
}
