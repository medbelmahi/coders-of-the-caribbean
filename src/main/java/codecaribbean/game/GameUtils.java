package codecaribbean.game;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by MedBelmahi on 20/04/2017.
 */
public class GameUtils {
    @SafeVarargs
    public static final <T> String join(T... v) {
        return Stream.of(v).map(String::valueOf).collect(Collectors.joining(" "));
    }
}
