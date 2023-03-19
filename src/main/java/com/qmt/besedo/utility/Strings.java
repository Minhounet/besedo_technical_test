package com.qmt.besedo.utility;

import io.vavr.Function1;
import io.vavr.control.Option;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import static java.lang.Math.toIntExact;

/**
 * Utility class for {@link String} objects
 */
@UtilityClass
public class Strings {

    /**
     * Count nb of vowels excluding "y" which is not a trivial case.
     * @see #GET_VOWELS_COUNT
     */
    private static final Function1<String, Integer> INTERNAL_GET_VOWELS_COUNT = string -> {
        var effectiveString = Option.of(string).getOrElse("");
        // We will be able to detect vowels on diacritics for lower and upper cases
        var cleanString = StringUtils.stripAccents(effectiveString).toLowerCase();
        // "y" is a serious problem as we must be able to split word into syllables, we are not this case, so nb of vowels may be inaccurate
        return toIntExact(cleanString.chars()
                .filter(c -> c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
                .count());
    };

    /**
     * Count nb of vowels excluding "y" which is not a trivial case. Function is memoized for better performance.
     * @see #INTERNAL_GET_VOWELS_COUNT
     */
    public static final Function1<String, Integer> GET_VOWELS_COUNT = INTERNAL_GET_VOWELS_COUNT.memoized();
}
