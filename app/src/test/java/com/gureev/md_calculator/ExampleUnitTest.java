package com.gureev.md_calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(10,5+5);

        //тест модели данных
        ResOfCalc resOfCalc =  new ResOfCalc();
        resOfCalc.expr = "10+10";
        assertEquals(resOfCalc.getExpr(), "10+10");
    }
}