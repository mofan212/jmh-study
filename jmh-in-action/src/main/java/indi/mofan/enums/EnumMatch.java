package indi.mofan.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mofan
 * @date 2023/12/26 23:13
 */
public enum EnumMatch {
    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8),
    I(9),
    J(10),
    NULL(-1),
    ;

    private final Integer value;

    EnumMatch(Integer value) {
        this.value = value;
    }

    public static EnumMatch matchWithFor(Integer value) {
        for (EnumMatch enumMatch : values()) {
            if (enumMatch.value.equals(value)) {
                return enumMatch;
            }
        }
        return NULL;
    }

    private static final Map<Integer, EnumMatch> ENUM_MAP = new HashMap<>();

    static {
        for (EnumMatch enumMatch : values()) {
            ENUM_MAP.put(enumMatch.value, enumMatch);
        }
    }

    public static EnumMatch matchWithMap(Integer value) {
        EnumMatch result = ENUM_MAP.get(value);
        if (result == null) {
            result = EnumMatch.NULL;
        }
        return result;
    }
}
