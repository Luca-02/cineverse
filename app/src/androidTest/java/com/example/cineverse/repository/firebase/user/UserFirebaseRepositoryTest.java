package com.example.cineverse.repository.firebase.user;

import static org.junit.Assert.*;

import com.example.cineverse.repository.storage.firebase.user.UserFirebaseRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class UserFirebaseRepositoryTest {

    private static final String TAG = UserFirebaseRepositoryTest.class.getSimpleName();

    private UserFirebaseRepository userFirebaseRepository;

    @Before
    public void setup() {
        userFirebaseRepository = new UserFirebaseRepository();
    }

    @Test
    public void getEmailForUsername() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        String inputUsername = "milanesiluca2002";
        userFirebaseRepository.getEmailFromUsername(inputUsername, email -> {
            assertNotNull(email);
            assertEquals("milanesiluca2002@gmail.com", email);
            latch.countDown();
        });

        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void getUserFromUid() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        String inputUsername = "gVAAnz1sdReOF6p7lpvJwWoW7Z72";
        userFirebaseRepository.getUserFromUid(inputUsername, user -> {
            assertNotNull(user);
            assertEquals("gVAAnz1sdReOF6p7lpvJwWoW7Z72", user.getUid());
            assertEquals("milanesiluca2002@gmail.com", user.getEmail());
            assertEquals("milanesiluca2002", user.getUsername());
            assertEquals("https://lh3.googleusercontent.com/a/ACg8ocLXmtsf6S5Nr2xyfKmuvgsE_OzFALlgXUbv_BtoXJc0BFA=s96-c", user.getPhotoUrl());
            latch.countDown();
        });

        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void isUsernameSaved() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        String inputUsername = "milanesiluca2002";
        userFirebaseRepository.isUsernameSaved(inputUsername, exist -> {
            assertNotNull(exist);
            assertTrue(exist);
            latch.countDown();
        });

        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void isUsernameSaved2() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        String inputUsername = "test200";
        userFirebaseRepository.isUsernameSaved(inputUsername, exist -> {
            assertNotNull(exist);
            assertFalse(exist);
            latch.countDown();
        });

        latch.await(5, TimeUnit.SECONDS);
    }

}