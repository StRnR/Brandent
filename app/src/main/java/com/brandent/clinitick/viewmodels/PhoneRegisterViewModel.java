package com.brandent.clinitick.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.brandent.clinitick.api.models.MessageResponse;
import com.brandent.clinitick.api.repos.AuthRepository;

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
