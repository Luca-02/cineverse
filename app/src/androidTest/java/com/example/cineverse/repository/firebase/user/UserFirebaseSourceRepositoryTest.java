package com.example.cineverse.repository.firebase.user;

import static org.junit.Assert.*;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.cineverse.data.model.user.User;
import com.example.cineverse.data.query.user.UserFirebaseQuery;
import com.example.cineverse.data.service.firebase.UserFirebaseDatabaseServices;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class UserFirebaseSourceRepositoryTest {

    private static final String TAG = UserFirebaseSourceRepositoryTest.class.getSimpleName();

    private Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void getEmailForUsername() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        String inputUsername = "milanesiluca2002";
        UserFirebaseQuery.getEmailFromUsername(inputUsername, context,
                new UserFirebaseDatabaseServices.Callback<String>() {
                    @Override
                    public void onCallback(String email) {
                        assertNotNull(email);
                        assertEquals("milanesiluca2002@gmail.com", email);
                        latch.countDown();
                    }

                    @Override
                    public void onNetworkUnavailable() {
                        Log.d(TAG, "onNetworkUnavailable");
                    }
                });

        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void getUserFromUid() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        String inputUsername = "gVAAnz1sdReOF6p7lpvJwWoW7Z72";
        UserFirebaseQuery.getUserFromUid(inputUsername, context,
                new UserFirebaseDatabaseServices.Callback<User>() {
                    @Override
                    public void onCallback(User user) {
                        assertNotNull(user);
                        assertEquals("gVAAnz1sdReOF6p7lpvJwWoW7Z72", user.getUid());
                        assertEquals("milanesiluca2002@gmail.com", user.getEmail());
                        assertEquals("milanesiluca2002", user.getUsername());
                        assertEquals("https://lh3.googleusercontent.com/a/ACg8ocLXmtsf6S5Nr2xyfKmuvgsE_OzFALlgXUbv_BtoXJc0BFA=s96-c", user.getPhotoUrl());
                        latch.countDown();
                    }

                    @Override
                    public void onNetworkUnavailable() {
                        Log.d(TAG, "onNetworkUnavailable");
                    }
                });

        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void isUsernameSaved() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        String inputUsername = "milanesiluca2002";
        UserFirebaseQuery.isUsernameSaved(inputUsername, context,
                new UserFirebaseDatabaseServices.Callback<Boolean>() {
                    @Override
                    public void onCallback(Boolean exist) {
                        assertNotNull(exist);
                        assertTrue(exist);
                        latch.countDown();
                    }

                    @Override
                    public void onNetworkUnavailable() {
                        Log.d(TAG, "onNetworkUnavailable");
                    }
                });

        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void isUsernameSaved2() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        String inputUsername = "test200";
        UserFirebaseQuery.isUsernameSaved(inputUsername, context,
                new UserFirebaseDatabaseServices.Callback<Boolean>() {
                    @Override
                    public void onCallback(Boolean exist) {
                        assertNotNull(exist);
                        assertTrue(exist);
                        latch.countDown();
                    }

                    @Override
                    public void onNetworkUnavailable() {
                        Log.d(TAG, "onNetworkUnavailable");
                    }
                });

        latch.await(5, TimeUnit.SECONDS);
    }

}