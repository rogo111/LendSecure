package com.friendlyphire.lendsecure;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void checkValid_isCorrect() throws Exception{
        assertTrue(PhoneUtils.checkValid("+32499223344"));
        assertFalse(PhoneUtils.checkValid("++32499223344"));
        assertFalse(PhoneUtils.checkValid("00032499223344"));
        assertFalse(PhoneUtils.checkValid("00+32499223344"));
        assertFalse(PhoneUtils.checkValid("*32499223344"));
        assertFalse(PhoneUtils.checkValid("#+32499223344"));
    }
}