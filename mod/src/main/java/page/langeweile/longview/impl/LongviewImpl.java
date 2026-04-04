package page.langeweile.longview.impl;

import org.lwjgl.opengl.GL45;
import page.langeweile.longview.mixin.reverse_z.compat.iris.IrisApiAccessor;

public class LongviewImpl {
	private static final Object IRIS_API_INSTANCE;

	static {
		Object instance;
		try {
			instance = LongviewImpl.getClass("net.irisshaders.iris.api.v0.IrisApi").getMethod("getInstance").invoke(null);
		} catch (Exception e) {
			instance = null;
		}
		IRIS_API_INSTANCE = instance;
	}

	public static boolean isZZeroToOne() {
		return !isIrisActive();
	}

	public static boolean isZReversed() {
		return !isIrisActive();
	}

	private static boolean isIrisActive() {
		if (IRIS_API_INSTANCE != null) {
			return ((IrisApiAccessor) IRIS_API_INSTANCE).callIsShaderPackInUse();
		}

		return false;
	}

	private static Class<?> getClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public static void toggleZZeroToOne(boolean zZeroToOne) {
		if (zZeroToOne) {
			GL45.glClipControl(GL45.GL_LOWER_LEFT, GL45.GL_ZERO_TO_ONE);
		} else {
			GL45.glClipControl(GL45.GL_LOWER_LEFT, GL45.GL_NEGATIVE_ONE_TO_ONE);
		}
	}
}
