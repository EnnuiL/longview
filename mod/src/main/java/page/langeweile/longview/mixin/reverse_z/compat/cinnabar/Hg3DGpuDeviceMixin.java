/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
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
