package com.example.cineverse.repository.classes;

import androidx.lifecycle.MutableLiveData;

/**
 * The {@link AbstractNetworkRepository} class serves as a base class for repositories that handle
 * network-related operations. It includes a {@link MutableLiveData} for observing network errors.
 */
public abstract class AbstractNetworkRepository {

    private final MutableLiveData<Boolean> networkErrorLiveData;

    public AbstractNetworkRepository() {
        networkErrorLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getNetworkErrorLiveData() {
        return networkErrorLiveData;
    }

    public void setNetworkErrorLiveData(boolean bool) {
        networkErrorLiveData.postValue(bool);
    }

}
