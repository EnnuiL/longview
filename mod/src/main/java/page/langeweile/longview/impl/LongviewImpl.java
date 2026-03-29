package page.langeweile.longview.impl;

public class LongviewImpl {
	//private OperationMode operationMode = OperationMode.LONGVIEW;

	public static Mode getMode() {
		return Mode.LONGVIEW;
	}

	public static boolean isZZeroToOne() {
		return true;
	}

	public static boolean isZReversed() {
		return true;
	}

	public enum Mode {
		VANILLA,
		HALFLONGVIEW,
		LONGVIEW
	}
}
