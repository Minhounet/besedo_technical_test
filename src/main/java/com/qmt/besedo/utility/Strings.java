package com.qmt.besedo.utility;

import io.vavr.Function1;
import io.vavr.Function3;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;

import static io.vavr.control.Validation.invalid;
import static io.vavr.control.Validation.valid;
import static java.lang.Math.toIntExact;

/**
 * Utility class for {@link String} objects
 */
@UtilityClass
public class Strings {

    /**
     * Return Option String if input String is not blank and non-null.
     */
    public static final Function<String, Option<String>> GET_NON_EMPTY_VALUE = value ->
            Option.of(value)
                    .flatMap(v -> v.isBlank() ? Option.none() : Option.of(v));
    /**
     * Require one on two values to be non-blank, if not return error message.
     */
    public static final Function3<String, String, String, Validation<String, Tuple2<String, String>>> REQUIRE_ONE_NON_BLANK_FROM_TWO =
            // Could be generalized using a list.
            (value1, value2, errorMessage) -> {
                Option<String> op1 = GET_NON_EMPTY_VALUE.apply(value1);
                Option<String> opt2 = GET_NON_EMPTY_VALUE.apply(value2);

                if (op1.isEmpty() && opt2.isEmpty()) {
                    return invalid(errorMessage);
                } else {
                    return valid(Tuple.of(value1, value2));
                }
            };
    /**
     * Return String length. If String is blank, return 0
     */
    public static final ToIntFunction<String> GET_SIZE = v -> v == null || v.isBlank() ? 0 : v.length();
    /**
     * Count nb of vowels excluding "y" which is not a trivial case.
     *
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
     *
     * @see #INTERNAL_GET_VOWELS_COUNT
     */
    public static final Function1<String, Integer> GET_VOWELS_COUNT = INTERNAL_GET_VOWELS_COUNT.memoized();

    /**
     * Return the incoming String if not blank, return "Unexpected error" otherwise.
     */
    public static final UnaryOperator<String> BUILD_ERROR_MESSAGE = error -> null == error || error.isBlank() ? "Unexpected error" : error;
}
