package com.pixium.brandent.api;

import com.pixium.brandent.api.models.sync.SyncRequest;
import com.pixium.brandent.api.models.sync.SyncResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SyncApi {
    @POST("sync")
    Call<SyncResponse> sync(@Body SyncRequest syncRequest);
}
