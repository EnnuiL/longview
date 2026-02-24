package page.langeweile.longview.mixin.reverse_z;

import com.mojang.blaze3d.systems.CommandEncoder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.OptionalDouble;

@Mixin(CommandEncoder.class)
public class CommandEncoderMixin {
    @ModifyVariable(
            method = "createRenderPass(Ljava/util/function/Supplier;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalInt;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalDouble;)Lcom/mojang/blaze3d/systems/RenderPass;",
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
