package com.example.cineverse.viewmodel.verified_account;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.CurrentUser;
import com.example.cineverse.data.model.User;
import com.example.cineverse.repository.auth.logged.VerifiedAccountRepository;
import com.example.cineverse.viewmodel.AbstractLoggedViewModel;

/**
 * The {@link VerifiedAccountViewModel} class represents the ViewModel for a verified user's account.
 * It extends the {@link AbstractLoggedViewModel} class and provides access to the associated repository.
 */
public class VerifiedAccountViewModel
        extends AbstractLoggedViewModel<VerifiedAccountRepository>
        implements VerifiedAccountRepository.UsernameChangeCallback {

    private MutableLiveData<Boolean> usernameChangedLiveData;
    private MutableLiveData<VerifiedAccountRepository.Error> changeUsernameErrorLiveData;

    /**
     * Constructs a {@link VerifiedAccountViewModel} object with the given {@link Application}.
     *
     * @param application The {@link Application} of the calling component.
     */
    public VerifiedAccountViewModel(@NonNull Application application) {
        super(application, new VerifiedAccountRepository(application.getApplicationContext()));
        userRepository.setUsernameChangeCallback(this);
    }

    public MutableLiveData<Boolean> getUsernameChangedLiveData() {
        if (usernameChangedLiveData == null) {
            usernameChangedLiveData = new MutableLiveData<>();
        }
        return usernameChangedLiveData;
    }

    public MutableLiveData<VerifiedAccountRepository.Error> getChangeUsernameErrorLiveData() {
        if (changeUsernameErrorLiveData == null) {
            changeUsernameErrorLiveData = new MutableLiveData<>();
        }
        return changeUsernameErrorLiveData;
    }

    public void changeUsername(String newUsername) {
        userRepository.changeUsernameOfUser(newUsername);
    }

    @Override
    public void onUsernameChange(User user) {
        getUsernameChangedLiveData().postValue(user != null);
        CurrentUser.getInstance().getCurrentUserMutableLiveData().postValue(user);
    }

    @Override
    public void onError(VerifiedAccountRepository.Error error) {
        getChangeUsernameErrorLiveData().postValue(error);
    }

}
