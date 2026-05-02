/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.impl;

import net.irisshaders.iris.api.v0.IrisApi;
import org.lwjgl.opengl.GL45;

public class LongviewImpl {
	private static final Object IRIS_API_INSTANCE;

	private static boolean supportsGlClipControl = false;

	static {
		Object instance;
		try {
			instance = LongviewImpl.getClass("net.irisshaders.iris.api.v0.IrisApi").getMethod("getInstance").invoke(null);
		} catch (Exception e) {
			instance = null;
		}
		IRIS_API_INSTANCE = instance;
	}

	public static boolean isGlZZeroToOne() {
		return !isIrisActive();
	}

	public static boolean isZReversed() {
		return !isIrisActive();
	}

	private static boolean isIrisActive() {
		if (IRIS_API_INSTANCE != null) {
			return ((IrisApi) IRIS_API_INSTANCE).isShaderPackInUse();
		}

		return false;
	}

	public static void markGlClipControlSupport() {
		LongviewImpl.supportsGlClipControl = true;
	}

	private static Class<?> getClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public static void toggleZZeroToOne(boolean zZeroToOne) {
		if (LongviewImpl.supportsGlClipControl) {
			if (zZeroToOne) {
				GL45.glClipControl(GL45.GL_LOWER_LEFT, GL45.GL_ZERO_TO_ONE);
			} else {
				GL45.glClipControl(GL45.GL_LOWER_LEFT, GL45.GL_NEGATIVE_ONE_TO_ONE);
			}
		}
	}
}
