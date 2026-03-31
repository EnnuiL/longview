package page.langeweile.longview.impl;

import org.lwjgl.opengl.GL45;
import page.langeweile.longview.mixin.reverse_z.compat.IrisApiAccessor;

public class LongviewImpl {
	public static boolean isZZeroToOne() {
		return !isIrisActive();
	}

	public static boolean isZReversed() {
		return !isIrisActive();
	}

	private static boolean isIrisActive() {
		try {
			return ((IrisApiAccessor) LongviewImpl.getClass("net.irisshaders.iris.api.v0.IrisApi").getMethod("getInstance").invoke(null)).callIsShaderPackInUse();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static Class<?> getClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	private static void toggleZZeroToOne(boolean zZeroToOne) {
		if (zZeroToOne) {
			GL45.glClipControl(GL45.GL_LOWER_LEFT, GL45.GL_ZERO_TO_ONE);
		} else {
			GL45.glClipControl(GL45.GL_LOWER_LEFT, GL45.GL_NEGATIVE_ONE_TO_ONE);
		}
	}

	public enum Mode {
		VANILLA,
		HALFLONGVIEW,
		LONGVIEW
	}
}
