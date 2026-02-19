package page.langeweile.longview.mixin.reverse_z.compat.cinnabar;

import org.spongepowered.asm.mixin.Mixin;
import page.langeweile.longview.api.LongviewDevice;

@Mixin(targets = "graphics.cinnabar.core.hg3d.Hg3DGpuDevice")
public class Hg3DGpuDeviceMixin implements LongviewDevice {
    @Override
    public boolean supportsReverseZ() {
        return true;
    }
}
