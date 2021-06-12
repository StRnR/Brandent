package com.brandent.clinitick.api;

import com.brandent.clinitick.api.models.sync.SyncRequest;
import com.brandent.clinitick.api.models.sync.SyncResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SyncApi {
    @POST("sync")
    Call<SyncResponse> sync(@Header("token") String token, @Body SyncRequest syncRequest);
}
