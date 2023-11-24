package com.example.cineverse.repository.storage.firebase.user;

import androidx.annotation.NonNull;

import com.example.cineverse.model.User;
import com.example.cineverse.repository.storage.firebase.AbstractUserFirebaseRepository;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class UserFirebaseRepository
        extends AbstractUserFirebaseRepository {
    
    public void saveUser(FirebaseUser firebaseUser, String username, Callback<Boolean> callback) {
        isUserSaved(firebaseUser.getUid(), exist -> {
            if (exist == null) {
                callback.onCallback(null);
            } else {
                if (!exist) {
                    User user = new User(firebaseUser, username);
                    usernamesDatabase.child(user.getUsername()).setValue(user.getUid());
                    Map<String, Object> userMap = user.createUserMap();
                    usersDatabase.child(user.getUid()).setValue(userMap);
                }
                callback.onCallback(!exist);
            }
        });
    }

    public void isUsernameSaved(String username, UserFirebaseRepository.Callback<Boolean> callback) {
        Query query = usernamesDatabase.child(username);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean exists = dataSnapshot.exists();
                callback.onCallback(exists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCallback(null);
            }
        });
    }

    public void isUserSaved(String uid, UserFirebaseRepository.Callback<Boolean> callback) {
        Query query = usersDatabase.child(uid);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean exists = dataSnapshot.exists();
                callback.onCallback(exists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCallback(null);
            }
        });
    }

    public void getUserFromUid(String uid, final UserFirebaseRepository.Callback<User> callback) {
        Query query = usersDatabase.child(uid);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    callback.onCallback(user);
                } else {
                    callback.onCallback(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onCallback(null);
            }
        });
    }

    public void getEmailFromUsername(String username, final UserFirebaseRepository.Callback<String> callback) {
        Query uidQuery = usernamesDatabase.child(username);

        uidQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String uid = dataSnapshot.getValue(String.class);
                    if (uid != null) {
                        getEmailFromUid(uid, callback);
                    } else {
                        callback.onCallback(null);
                    }
                } else {
                    callback.onCallback(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCallback(null);
            }
        });
    }

    public void getEmailFromUid(String uid, final UserFirebaseRepository.Callback<String> callback) {
        Query emailQuery = usersDatabase.child(uid).child("email");

        emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String email = dataSnapshot.getValue(String.class);
                    callback.onCallback(email);
                } else {
                    callback.onCallback(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCallback(null);
            }
        });
    }

}
