package com.pixium.brandent.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pixium.brandent.api.models.MessageResponse;
import com.pixium.brandent.api.repos.AuthRepository;

public class PhoneRegisterViewModel extends ViewModel {

    private MutableLiveData<MessageResponse> mutableLiveData;

    public void init(String phone) {
        AuthRepository authRepository = AuthRepository.getInstance();
        mutableLiveData = authRepository.postPhone(phone);
    }


    public LiveData<MessageResponse> postPhone() {
        return mutableLiveData;
    }
}
