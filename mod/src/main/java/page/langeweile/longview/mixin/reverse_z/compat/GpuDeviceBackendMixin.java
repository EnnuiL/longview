/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.reverse_z.compat;

import com.mojang.blaze3d.systems.GpuDeviceBackend;
import org.spongepowered.asm.mixin.Mixin;
import page.langeweile.longview.api.LongviewDevice;

@Mixin(GpuDeviceBackend.class)
public interface GpuDeviceBackendMixin extends LongviewDevice {}
