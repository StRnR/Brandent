package com.pixium.brandent.api;

import com.pixium.brandent.api.models.MessageResponse;
import com.pixium.brandent.api.models.auth.AuthResponse;
import com.pixium.brandent.api.models.auth.CodeRequest;
import com.pixium.brandent.api.models.auth.LoginRequest;
import com.pixium.brandent.api.models.auth.PhoneRequest;
import com.pixium.brandent.api.models.auth.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("auth/register/phone")
    Call<MessageResponse> registerPhone(@Body PhoneRequest phoneRequest);

    @POST("auth/register/code")
    Call<MessageResponse> registerCode(@Body CodeRequest codeRequest);

    @POST("auth/register")
    Call<AuthResponse> registerDentist(@Body RegisterRequest registerRequest);

    @POST("auth/login")
    Call<AuthResponse> loginDentist(@Body LoginRequest loginRequest);
}
