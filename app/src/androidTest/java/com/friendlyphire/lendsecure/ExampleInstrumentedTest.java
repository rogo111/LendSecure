package com.friendlyphire.lendsecure;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    /*
    @pre: PhoneUtils.getCountryZipCode() returns a country code 32
     */
    @Test
    public void checkPremiumBE_isCorrect() throws Exception{
        //format +
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"+3209019003838"));
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"+329003838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"+32091083838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"+329113838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"+32468013838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"+3211915413838"));
        //format 00
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"003209019003838"));
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"00329003838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"0032091083838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"00329113838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"0032468013838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"003211915413838"));
        //format no CC
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"09019003838"));
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"9003838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"091083838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"9113838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"468013838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"11915413838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"0468013838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"011915413838"));
    }

    @Test
    public void checkPremium_isCorrect() throws Exception{
        //format +
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"+119003838"));
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"+19003838"));
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"+15403838"));
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"+19763838"));
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"+18093838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"+18083838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"+119013838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"+19013838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"+15413838"));
        //format 00
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"00119003838"));
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"0019003838"));
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"0015403838"));
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"0019763838"));
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"0018093838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"0018083838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"00119013838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"0019013838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"0015413838"));
        //format no CC
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"19003838"));
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"9003838"));
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"5403838"));
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"9763838"));
        assertTrue(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"8093838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"18083838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"19013838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"9013838"));
        assertFalse(PhoneUtils.checkPremium(InstrumentationRegistry.getTargetContext(),"5413838"));


    }

    @Test
    public void checkForeign_isCorrect() throws Exception{
        //test no countrycode
        assertFalse(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"034823749832"));
        assertFalse(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"34823749832"));

        //test noneu-othernoneu
        assertTrue(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"009724823749832"));
        assertTrue(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"+972134823749832"));
        assertTrue(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"00734823749832"));
        assertTrue(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"+734823749832"));
        //test noneu-samenoneu
        assertFalse(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"00134823749832"));
        assertFalse(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"+134823749832"));

        //test noneu-eu
        assertTrue(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"004434823749832"));
        assertTrue(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"+4434823749832"));
        assertTrue(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"003134823749832"));
        assertTrue(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"+3134823749832"));
    }

    /*
    @pre: PhoneUtils.getCountryZipCode() returns a country code within the EU
     */
    @Test
    public void checkForeign_isCorrectEU() throws Exception{
        //test eu-eu
        assertFalse(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"004434823749832"));
        assertFalse(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"+4434823749832"));
        assertFalse(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"003134823749832"));
        assertFalse(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"+3134823749832"));
        //test eu-noneu
        assertTrue(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"00134823749832"));
        assertTrue(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"+134823749832"));
        assertTrue(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"00734823749832"));
        assertTrue(PhoneUtils.checkForeign(InstrumentationRegistry.getTargetContext(),"+734823749832"));
    }

    @Test
    public void splitNumber_isCorrect() throws Exception {

        Log.i("tagcountrycode",PhoneUtils.getCountryZipCode(InstrumentationRegistry.getTargetContext()));
        String[] split;
        split = PhoneUtils.splitNumber(InstrumentationRegistry.getTargetContext(),"+32495888888");
        assertTrue(split[0].equals("32"));
        assertTrue(split[1].equals("495888888"));

        split = PhoneUtils.splitNumber(InstrumentationRegistry.getTargetContext(),"0032495888888");
        assertTrue(split[0].equals("32"));
        assertTrue(split[1].equals("495888888"));

        split = PhoneUtils.splitNumber(InstrumentationRegistry.getTargetContext(),"32495888888");
        assertTrue(split[0].equals(PhoneUtils.getCountryZipCode(InstrumentationRegistry.getTargetContext())));
        assertTrue(split[1].equals("32495888888"));
    }
}
