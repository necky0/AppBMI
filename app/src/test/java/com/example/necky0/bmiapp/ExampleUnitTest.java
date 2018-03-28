package com.example.necky0.bmiapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void polishUnits_isCorrect() throws Exception {
        assertEquals(22.530, BMICount.polishUnits(69, 1.75), 0.001);
    }

    @Test
    public void englishUnits_isCorrect() throws Exception {
        assertEquals(23.433, BMICount.englishUnits(120, 60), 0.001);
    }
}