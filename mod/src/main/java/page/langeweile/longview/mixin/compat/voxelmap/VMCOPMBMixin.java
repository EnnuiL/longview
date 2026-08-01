package page.langeweile.longview.mixin.compat.voxelmap;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import page.langeweile.longview.impl.LongviewImpl;

// This has been fixed upstream, but it looks like there are no plans to release another 26.1 update
// Therefore, we'll patch VoxelMap Updated ourselves for now
@Mixin(targets = "com.mamiyaotaru.voxelmap.util.VoxelMapCachedOrthoProjectionMatrixBuffer")
public abstract class VMCOPMBMixin {
	@WrapOperation(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lorg/joml/Matrix4f;setOrtho(FFFFFF)Lorg/joml/Matrix4f;"
		)
	)
	private Matrix4f invertOrthogonalMatrixZ(Matrix4f instance, float left, float right, float bottom, float top, float zNear, float zFar, Operation<Matrix4f> original) {
		return LongviewImpl.isZReversed()
			? instance.setOrtho(left, right, bottom, top, zFar, zNear, RenderSystem.getDevice().isZZeroToOne())
			: instance.setOrtho(left, right, bottom, top, zNear, zFar, RenderSystem.getDevice().isZZeroToOne());
	}
}
