/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL45;

public class LongviewImpl {
	private static boolean supportsGlClipControl = false;

	private static boolean enableIrisReverseZ = true;
	private static boolean enableIrisZZeroToOne = true;

	public static boolean isGlZZeroToOne() {
		return enableIrisZZeroToOne;
	}

	public static boolean supportsGlClipControl() {
		return supportsGlClipControl;
	}

	public static boolean isZReversed() {
		return enableIrisReverseZ;
	}

	public static void setIrisLongview(boolean reverseZ, boolean zZeroToOne) {
		if (LongviewImpl.enableIrisZZeroToOne != zZeroToOne) {
			// This is only applicable to the OpenGL backend
			if (RenderSystem.tryGetDevice() != null && RenderSystem.tryGetDevice().getBackendName().equals("OpenGL")) {
				LongviewImpl.toggleZZeroToOne(LongviewImpl.isGlZZeroToOne());
			}
		}

		LongviewImpl.enableIrisReverseZ = reverseZ;
		LongviewImpl.enableIrisZZeroToOne = zZeroToOne;
	}

	public static void markGlClipControlSupport() {
		LongviewImpl.supportsGlClipControl = true;
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
