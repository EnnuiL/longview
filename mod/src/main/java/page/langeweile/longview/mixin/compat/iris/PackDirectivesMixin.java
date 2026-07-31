/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.compat.iris;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import page.langeweile.longview.impl.compat.LongviewPackDirectives;
import page.langeweile.longview.impl.compat.LongviewShaderProperties;

import java.util.Set;

@Mixin(targets = "net.irisshaders.iris.shaderpack.properties.PackDirectives")
public class PackDirectivesMixin implements LongviewPackDirectives {
	@Unique
	private boolean longviewEnableReverseZ = false;

	@Unique
	private boolean longviewEnableZZeroToOne = false;

	@Inject(method = "<init>(Ljava/util/Set;Lnet/irisshaders/iris/shaderpack/properties/ShaderProperties;)V", at = @At("TAIL"))
	private void injectLongviewProperties(Set<Integer> supportedRenderTargets, @Coerce Object properties, CallbackInfo ci) {
		this.longviewEnableReverseZ = ((LongviewShaderProperties) properties).getEnableReverseZ().orElse(false);
		this.longviewEnableZZeroToOne = ((LongviewShaderProperties) properties).getEnableZZeroToOne().orElse(false);
	}

	@Inject(method = "<init>(Ljava/util/Set;Lnet/irisshaders/iris/shaderpack/properties/PackDirectives;)V", at = @At("TAIL"))
	private void injectLongviewDirectives(Set<Integer> supportedRenderTargets, @Coerce Object directives, CallbackInfo ci) {
		this.longviewEnableReverseZ = ((LongviewPackDirectives) directives).enableReverseZ();
		this.longviewEnableZZeroToOne = ((LongviewPackDirectives) directives).enableZZeroToOne();
	}

	@Override
	public boolean enableReverseZ() {
		return this.longviewEnableReverseZ;
	}

	@Override
	public boolean enableZZeroToOne() {
		return this.longviewEnableZZeroToOne;
	}
}
