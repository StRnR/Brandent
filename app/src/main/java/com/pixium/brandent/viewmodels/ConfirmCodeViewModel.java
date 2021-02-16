package com.pixium.brandent.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pixium.brandent.api.models.MessageResponse;
import com.pixium.brandent.api.repos.AuthRepository;

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
