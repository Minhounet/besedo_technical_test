package com.qmt.besedo.utility;

import org.junit.jupiter.api.Test;

import static com.qmt.besedo.utility.Strings.GET_VOWELS_COUNT;
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
}