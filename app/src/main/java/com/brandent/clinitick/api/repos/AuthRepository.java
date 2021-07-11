package com.brandent.clinitick.api.repos;

import androidx.lifecycle.MutableLiveData;

import com.brandent.clinitick.api.AuthApi;
import com.brandent.clinitick.api.MainService;
import com.brandent.clinitick.api.models.MessageResponse;
import com.brandent.clinitick.api.models.auth.AuthResponse;
import com.brandent.clinitick.api.models.auth.CodeRequest;
import com.brandent.clinitick.api.models.auth.LoginRequest;
import com.brandent.clinitick.api.models.auth.PhoneRequest;
import com.brandent.clinitick.api.models.auth.RegisterRequest;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private static AuthRepository authRepository;
    private final AuthApi authApi;


    public AuthRepository() {
        authApi = MainService.createService(AuthApi.class);
    }

    public static AuthRepository getInstance() {
        if (authRepository == null) {
            authRepository = new AuthRepository();
        }
        return authRepository;
    }

    public MutableLiveData<MessageResponse> postPhone(String phone) {
        PhoneRequest phoneRequest = new PhoneRequest(phone);
        MutableLiveData<MessageResponse> messageResponse = new MutableLiveData<>();

        authApi.registerPhone(phoneRequest).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NotNull Call<MessageResponse> call
                    , @NotNull Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    messageResponse.setValue(response.body());
                } else {
                    MessageResponse unsuccessful = new MessageResponse("Unsuccessful!");
                    messageResponse.setValue(unsuccessful);
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageResponse> call, @NotNull Throwable t) {
                messageResponse.setValue(null);
            }
        });

        return messageResponse;
    }

    public MutableLiveData<MessageResponse> postCode(String phone, String code) {
        CodeRequest codeRequest = new CodeRequest(phone, code);
        MutableLiveData<MessageResponse> messageResponse = new MutableLiveData<>();

        authApi.registerCode(codeRequest).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NotNull Call<MessageResponse> call
                    , @NotNull Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    messageResponse.setValue(response.body());
                } else {
                    MessageResponse unsuccessful = new MessageResponse("Unsuccessful!");
                    messageResponse.setValue(unsuccessful);
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageResponse> call, @NotNull Throwable t) {
                messageResponse.setValue(null);
            }
        });

        return messageResponse;
    }

    public MutableLiveData<AuthResponse> registerDentist(RegisterRequest registerRequest) {
        MutableLiveData<AuthResponse> authResponse = new MutableLiveData<>();

        authApi.registerDentist(registerRequest).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(@NotNull Call<AuthResponse> call
                    , @NotNull Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    authResponse.setValue(response.body());
                } else {
                    authResponse.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                authResponse.setValue(null);
            }
        });

        return authResponse;
    }

    public MutableLiveData<AuthResponse> loginDentist(LoginRequest loginRequest) {
        MutableLiveData<AuthResponse> authResponse = new MutableLiveData<>();

        authApi.loginDentist(loginRequest).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(@NotNull Call<AuthResponse> call
                    , @NotNull Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    authResponse.setValue(response.body());
                } else {
                    authResponse.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                authResponse.setValue(null);
            }
        });

        return authResponse;
    }
}
