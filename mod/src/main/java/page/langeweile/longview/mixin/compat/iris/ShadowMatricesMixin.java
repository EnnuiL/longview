package page.langeweile.longview.mixin.compat.iris;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import page.langeweile.longview.impl.LongviewImpl;

@Mixin(targets = "net.irisshaders.iris.shadows.ShadowMatrices")
public abstract class ShadowMatricesMixin {
	@WrapOperation(
		method = "createOrthoMatrix",
		at = @At(
			value = "INVOKE",
			target = "Lorg/joml/Matrix4f;setOrthoSymmetric(FFFFZ)Lorg/joml/Matrix4f;"
		)
	)
	private static Matrix4f invertOrthogonalMatrixZ(Matrix4f instance, float width, float height, float zNear, float zFar, boolean zZeroToOne, Operation<Matrix4f> original) {
		return LongviewImpl.isZReversed()
			? original.call(instance, width, height, zFar, zNear, zZeroToOne)
			: original.call(instance, width, height, zNear, zFar, zZeroToOne);
	}

	// TODO - Ugh, I hope I don't have to support legacy shadow matrices..
}
