package page.langeweile.longview.api;

public interface LongviewDevice {
    default boolean supportsReverseZ() {
        return false;
    }
}
