package page.langeweile.longview.mixin.compat;

import com.mojang.blaze3d.systems.GpuDevice;
import org.spongepowered.asm.mixin.Mixin;
import page.langeweile.longview.api.LongviewDevice;

@Mixin(GpuDevice.class)
public abstract class GpuDeviceMixin implements LongviewDevice {}
