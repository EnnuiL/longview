package page.langeweile.longview.mixin.reverse_z;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.shaders.ShaderType;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @ModifyReturnValue(method = "lambda$new$2", at = @At("TAIL"))
    private String modifyShader(String original, @Local(argsOnly = true) Identifier id, @Local(argsOnly = true) ShaderType type) {
        if (type == ShaderType.FRAGMENT) {
            if (id.getPath().contains("transparency")) {
                if (original.contains("try_insert")) {
                    return original.replace("depth_layers[jj] > depth_layers[ii]", "depth_layers[jj] < depth_layers[ii]");
                }
            }
        }

        return original;
    }
}
