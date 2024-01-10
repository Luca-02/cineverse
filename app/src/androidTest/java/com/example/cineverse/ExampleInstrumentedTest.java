package com.example.cineverse;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String TAG = ExampleInstrumentedTest.class.getSimpleName();

    private Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void getLocalRegionTest() {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String ISO2 = manager.getSimCountryIso();
        String ISO3 = new Locale("", ISO2).getISO3Country();
        Log.d(TAG, ISO2);
        Log.d(TAG, ISO3);
        assertTrue(true);
    }

}