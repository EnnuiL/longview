/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package net.irisshaders.iris.helpers;

public enum OptionalBoolean {
	DEFAULT,
	FALSE,
	TRUE;

	public boolean orElse(boolean defaultValue) {
		return switch (this) {
			case DEFAULT -> defaultValue;
			case FALSE -> false;
			case TRUE -> true;
		};
	}
}
