package com.example.cineverse.repository.interfaces.auth;

import com.example.cineverse.R;
import com.example.cineverse.repository.interfaces.IUser;

/**
 * The {@link IAuth} interface extends the {@link IUser} interface, providing a comprehensive contract
 * for authentication-related services.
 */
public interface IAuth extends IUser {

    /**
     * {@link Error Error} enum representing possible authentication errors and associated string resources for error messages.
     */
    enum Error {
        SUCCESS(null),
        ERROR_INVALID_EMAIL(R.string.invalid_email),
        ERROR_INVALID_USERNAME_FORMAT(R.string.invalid_username_format),
        ERROR_INVALID_EMAIL_FORMAT(R.string.invalid_email_format),
        ERROR_NOT_FOUND_DISABLED(R.string.user_not_found_disabled),
        ERROR_USERNAME_ALREADY_EXISTS(R.string.username_already_exist),
        ERROR_EMAIL_ALREADY_EXISTS(R.string.email_already_exist),
        ERROR_WEAK_PASSWORD(R.string.weak_password),
        ERROR_WRONG_PASSWORD(R.string.wrong_password),
        ERROR_INVALID_CREDENTIAL(R.string.invalid_credentials),
        ERROR_GOOGLE_SIGNIN_FAILED(R.string.google_signin_failed),
        ERROR_AUTHENTICATION_FAILED(R.string.authentication_failed);

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

}