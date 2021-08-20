package com.brandent.clinitick.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.brandent.clinitick.api.models.MessageResponse;
import com.brandent.clinitick.api.repos.AuthRepository;

public class ForgotPassViewModel extends ViewModel {
        private MutableLiveData<Integer> mutableLiveData;

    public void init(String phone) {
        AuthRepository authRepository = AuthRepository.getInstance();
        mutableLiveData = authRepository.forgotPass(phone);
    }

    public LiveData<Integer> postForgotPassPhone() {
        return mutableLiveData;
    }

}
