package com.example.cineverse.repository.auth.logged;

import android.content.Context;

import com.example.cineverse.R;
import com.example.cineverse.data.model.User;
import com.example.cineverse.data.source.user.UserCallback;
import com.example.cineverse.repository.auth.AbstractAuthRepository;
import com.example.cineverse.repository.auth.AbstractLoggedRepository;
import com.example.cineverse.service.NetworkCallback;
import com.example.cineverse.utils.UsernameValidator;

public class VerifiedAccountRepository
        extends AbstractLoggedRepository {

    /**
     * {@link AbstractAuthRepository.Error Error} enum representing possible authentication errors and associated string resources for error messages.
     */
    public enum Error {
        ERROR_USERNAME_ALREADY_EXISTS(R.string.username_already_exist),
        ERROR_INVALID_USERNAME_FORMAT(R.string.invalid_username_format);

        private final Integer stringId;

        Error(Integer stringId) {
            this.stringId = stringId;
        }

        public boolean isSuccess() {
            return stringId == null;
        }

        public int getError() {
            return stringId;
        }
    }

    private UsernameChangeCallback usernameChangeCallback;

    /**
     * Constructs a {@link VerifiedAccountRepository} object with the given application {@link Context}.
     *
     * @param context The application {@link Context} of the calling component.
     */
    public VerifiedAccountRepository(Context context) {
        super(context);
    }

    public void setUsernameChangeCallback(UsernameChangeCallback usernameChangeCallback){
        this.usernameChangeCallback = usernameChangeCallback;
    }

    public void changeUsernameOfUser(String newUsername) {
        if (!UsernameValidator.getInstance().isValid(newUsername)) {
            usernameChangeCallback.onError(Error.ERROR_INVALID_USERNAME_FORMAT);
        } else {
            User user = getCurrentUser();
            if (user != null) {
                firebaseSource.isUsernameSaved(context, newUsername, new UserCallback<Boolean>() {
                    @Override
                    public void onCallback(Boolean exist) {
                        if (exist == null) {
                            usernameChangeCallback.onUsernameChange(null);
                        } else if (exist) {
                            usernameChangeCallback.onError(Error.ERROR_USERNAME_ALREADY_EXISTS);
                        } else {
                            handleChangeUsername(user, newUsername);
                        }
                    }

                    @Override
                    public void onNetworkUnavailable() {
                        usernameChangeCallback.onNetworkUnavailable();
                    }
                });
            }
        }
    }

    private void handleChangeUsername(User user, String newUsername) {
        firebaseSource.changeUsernameOfUser(context, user, newUsername, new UserCallback<String>() {
            @Override
            public void onCallback(String newUsername) {
                if (newUsername != null) {
                    user.setUsername(newUsername);
                    localSource.updateUser(user);
                    usernameChangeCallback.onUsernameChange(user);
                } else {
                    usernameChangeCallback.onUsernameChange(null);
                }
            }

            @Override
            public void onNetworkUnavailable() {
                usernameChangeCallback.onNetworkUnavailable();
            }
        });
    }

    public interface UsernameChangeCallback extends NetworkCallback {
        void onUsernameChange(User updatedUser);
        void onError(VerifiedAccountRepository.Error error);
    }

}
