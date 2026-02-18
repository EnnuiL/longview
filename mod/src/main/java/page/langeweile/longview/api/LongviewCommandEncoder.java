package page.langeweile.longview.api;

// TODO - Maybe LongviewDevice would be better?
public interface LongviewCommandEncoder {
    default boolean supportsLongview() {
        return false;
    }
}
