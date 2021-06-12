package com.brandent.clinitick.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.brandent.clinitick.api.models.MessageResponse;
import com.brandent.clinitick.api.repos.AuthRepository;

public class ConfirmCodeViewModel extends ViewModel {

    private MutableLiveData<MessageResponse> codeResponse;
    private AuthRepository authRepository;

    public void init(String phone, String code) {
        authRepository = AuthRepository.getInstance();
        codeResponse = authRepository.postCode(phone, code);
    }

    public LiveData<MessageResponse> postCode() {
        return codeResponse;
    }

    public LiveData<MessageResponse> postPhone(String phone) {
        return authRepository.postPhone(phone);
    }
}
