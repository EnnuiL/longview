/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin.compat.iris;

import com.llamalad7.mixinextras.sugar.Local;
import net.irisshaders.iris.helpers.OptionalBoolean;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import page.langeweile.longview.impl.compat.LongviewShaderProperties;

import java.util.function.Consumer;

@Mixin(targets = "net.irisshaders.iris.shaderpack.properties.ShaderProperties")
public class ShaderPropertiesMixin implements LongviewShaderProperties {
	@Unique
	private OptionalBoolean longviewEnableReverseZ = OptionalBoolean.DEFAULT;

	@Unique
	private OptionalBoolean longviewEnableZZeroToOne = OptionalBoolean.DEFAULT;

	@Shadow
	private static void handleBooleanDirective(String key, String value, String expectedKey, Consumer<OptionalBoolean> handler) {}

	@Inject(
		method = "lambda$new$0",
		at = @At(
			value = "INVOKE",
			target = "Lnet/irisshaders/iris/shaderpack/properties/ShaderProperties;handleBooleanDirective(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/function/Consumer;)V",
			ordinal = 0
		)
	)
	private void insertLongviewProperty(Object keyObject, Object valueObject, CallbackInfo ci, @Local(ordinal = 0) String key, @Local(ordinal = 1) String value) {
		handleBooleanDirective(key, value, "longview.enable_reverse_z", bool -> this.longviewEnableReverseZ = bool);
		handleBooleanDirective(key, value, "longview.enable_z_zero_to_one", bool -> this.longviewEnableZZeroToOne = bool);
	}

	@Override
	public OptionalBoolean getEnableReverseZ() {
		return this.longviewEnableReverseZ;
	}

	@Override
	public OptionalBoolean getEnableZZeroToOne() {
		return this.longviewEnableZZeroToOne;
	}
}
