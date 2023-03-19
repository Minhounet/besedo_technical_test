package com.qmt.besedo.utility;

import io.vavr.Tuple;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

import static com.qmt.besedo.utility.Strings.*;
import static io.vavr.control.Validation.invalid;
import static io.vavr.control.Validation.valid;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StringsTest {

    @Test
    void GET_VOWELS_COUNT_WITH_4a_letter_EXPECTED_4() {
        assertEquals(4, GET_VOWELS_COUNT.apply("baabaa"));
    }

    @Test
    void GET_VOWELS_COUNT_WITH_4e_letter_EXPECTED_4() {
        assertEquals(4, GET_VOWELS_COUNT.apply("mmbeebee mmm"));
    }

    @Test
    void GET_VOWELS_COUNT_WITH_4i_letter_EXPECTED_4() {
        assertEquals(4, GET_VOWELS_COUNT.apply("mmbiib ii mmm"));
    }

    @Test
    void GET_VOWELS_COUNT_WITH_4o_letter_EXPECTED_4() {
        assertEquals(4, GET_VOWELS_COUNT.apply("mmbob ooo mmm"));
    }

    @Test
    void GET_VOWELS_COUNT_WITH_4u_letter_EXPECTED_4() {
        assertEquals(4, GET_VOWELS_COUNT.apply("mmuubb muumm"));
    }

    @Test
    void GET_VOWELS_COUNT_WITH_10_vowels_EXPECTED_10() {
        assertEquals(10, GET_VOWELS_COUNT.apply("mmbdgaajged muummiii o yy i"));
    }

    @Test
    void GET_NON_EMPTY_VALUE_WITH_null_value_EXPECTED_none_option() {
        assertEquals(Option.none(), GET_NON_EMPTY_VALUE.apply(null));
    }

    @Test
    void GET_NON_EMPTY_VALUE_WITH_empty_value_EXPECTED_option_none() {
        assertEquals(Option.none(), GET_NON_EMPTY_VALUE.apply(""));
    }

    @Test
    void GET_NON_EMPTY_VALUE_WITH_blank_value_EXPECTED_option_none() {
        assertEquals(Option.none(), GET_NON_EMPTY_VALUE.apply("   "));
    }

    @Test
    void GET_NON_EMPTY_VALUE_WITH_word_EXPECTED_option_word() {
        assertEquals(Option.of("word"), GET_NON_EMPTY_VALUE.apply("word"));
    }


    @Test
    void REQUIRE_ONE_NON_BLANK_FROM_TWO_WITH_both_null_values_EXPECTED_error() {
        assertEquals(invalid("This is an error"), REQUIRE_ONE_NON_BLANK_FROM_TWO.apply(null, null, "This is an error"));
    }

    @Test
    void REQUIRE_ONE_NON_BLANK_FROM_TWO_WITH_both_blank_values_EXPECTED_error() {
        assertEquals(invalid("This is an error"), REQUIRE_ONE_NON_BLANK_FROM_TWO.apply("   ", "       ", "This is an error"));
    }

    @Test
    void REQUIRE_ONE_NON_BLANK_FROM_one_blank_value_AND_one_null_EXPECTED_error() {
        assertEquals(invalid("This is an error"), REQUIRE_ONE_NON_BLANK_FROM_TWO.apply("   ", null, "This is an error"));
    }

    @Test
    void REQUIRE_ONE_NON_BLANK_FROM_one_null_value_AND_one_blank_EXPECTED_error() {
        assertEquals(invalid("This is an error"), REQUIRE_ONE_NON_BLANK_FROM_TWO.apply(null, "   ", "This is an error"));
    }

    @Test
    void REQUIRE_ONE_NON_BLANK_FROM_two_non_empty_values_EXPECTED_ValidationTuple2Object() {
        assertEquals(valid(Tuple.of("one","two")), REQUIRE_ONE_NON_BLANK_FROM_TWO.apply("one", "two", "This is an error"));
    }

}