package com.github.cichyvx.beginformatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BeginEndFormatterTest extends InputTrait {

    private BeginEndFormatter subject;

    @BeforeEach
    public void setup() {
        subject = new BeginEndFormatter();
    }


    @ParameterizedTest()
    @MethodSource("input")
    public void formatTest(String input, String expected) {
        assertEquals(expected, subject.format(input));
    }

}
