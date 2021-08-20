package com.brandent.clinitick.api;

import com.brandent.clinitick.api.models.MessageResponse;
import com.brandent.clinitick.api.models.auth.AuthResponse;
import com.brandent.clinitick.api.models.auth.CodeRequest;
import com.brandent.clinitick.api.models.auth.DentistGetImageResponse;
import com.brandent.clinitick.api.models.auth.LoginRequest;
import com.brandent.clinitick.api.models.auth.PhoneRequest;
import com.brandent.clinitick.api.models.auth.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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

    @POST("auth/forgot/phone")
    Call<Void> forgotPass(@Body PhoneRequest phoneRequest);

    @POST("auth/forgot/phone")

    @GET("dentists/profile/image")
    Call<DentistGetImageResponse> getDentistImage();
}
