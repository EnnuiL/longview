/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.Projection;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Projection.class)
public class ProjectionMixin {
	@WrapOperation(method = "getMatrix", at = @At(value = "INVOKE", target = "Lorg/joml/Matrix4f;setPerspective(FFFFZ)Lorg/joml/Matrix4f;"))
	private Matrix4f invertPerspectiveMatrixZ(Matrix4f instance, float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne, Operation<Matrix4f> original) {
		return original.call(instance, fovy, aspect, zFar, zNear, zZeroToOne);
	}

	@WrapOperation(method = "getMatrix", at = @At(value = "INVOKE", target = "Lorg/joml/Matrix4f;setOrtho(FFFFFFZ)Lorg/joml/Matrix4f;"))
	private Matrix4f invertOrthogonalMatrixZ(Matrix4f instance, float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne, Operation<Matrix4f> original) {
		return original.call(instance, left, right, bottom, top, zFar, zNear, zZeroToOne);
	}
}
