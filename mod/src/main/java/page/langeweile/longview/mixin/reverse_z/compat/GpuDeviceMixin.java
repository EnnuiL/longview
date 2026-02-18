/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.reverse_z.compat;

import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.GpuDeviceBackend;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import page.langeweile.longview.api.LongviewDevice;

@Mixin(GpuDevice.class)
public abstract class GpuDeviceMixin implements LongviewDevice {
    @Shadow
    @Final
    private GpuDeviceBackend backend;

    @Override
    public boolean supportsReverseZ() {
        return ((LongviewDevice) this.backend).supportsReverseZ();
    }
}
