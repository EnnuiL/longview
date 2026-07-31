/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package page.langeweile.longview.impl;

import org.lwjgl.opengl.GL45;

public class LongviewImpl {
	private static boolean supportsGlClipControl = false;

	private static boolean enableIrisReverseZ = true;
	private static boolean enableIrisZZeroToOne = true;

	public static boolean isGlZZeroToOne() {
		return enableIrisReverseZ;
	}

	public static boolean isZReversed() {
		return enableIrisZZeroToOne;
	}

	public static void setIrisLongview(boolean reverseZ, boolean zZeroToOne) {
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
