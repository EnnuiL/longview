package page.langeweile.longview.mixin.compat;

import com.mojang.blaze3d.systems.CommandEncoderBackend;
import org.spongepowered.asm.mixin.Mixin;
import page.langeweile.longview.api.LongviewCommandEncoder;

@Mixin(CommandEncoderBackend.class)
public interface CommandEncoderBackendMixin extends LongviewCommandEncoder {}
