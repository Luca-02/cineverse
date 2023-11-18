package com.example.cineverse;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.cineverse.Repository.Auth.GoogleAuthRepository;
import com.example.cineverse.ViewModel.Auth.AuthViewModel;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String TAG = ExampleInstrumentedTest.class.getSimpleName();

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.cineverse", appContext.getPackageName());
    }

    @org.junit.Test
    public void viewModelTest() {
//        Log.d("MY_TAG", String.valueOf(viewModel.getClass()));
//        Log.d("MY_TAG", viewModel.repository.toString());
//        Log.d("MY_TAG", String.valueOf(viewModel.repository == null));
//        Log.d("MY_TAG", String.valueOf(viewModel.repository.getClass()));
//        Log.d("MY_TAG", viewModel.getUserLiveData().toString());
//        Log.d("MY_TAG", String.valueOf(viewModel.getUserLiveData() == null));
//        Log.d("MY_TAG", viewModel.getNetworkErrorLiveData().toString());
//        Log.d("MY_TAG", String.valueOf(viewModel.getNetworkErrorLiveData() == null));
//        Log.d("MY_TAG", viewModel.getErrorLiveData().toString());
//        Log.d("MY_TAG", String.valueOf(viewModel.getErrorLiveData() == null));

        AuthViewModel viewModel = new AuthViewModel(new Application());
//        System.out.println(viewModel.getClass());
//        System.out.println(viewModel.repository);
//        System.out.println(viewModel.repository == null);
//        System.out.println(viewModel.repository.getClass());
//        System.out.println(viewModel.getUserLiveData());
//        System.out.println(viewModel.getUserLiveData() == null);
//        System.out.println(viewModel.getNetworkErrorLiveData());
//        System.out.println(viewModel.getNetworkErrorLiveData() == null);
//        System.out.println(viewModel.getErrorLiveData());
//        System.out.println(viewModel.getErrorLiveData() == null);
    }

}