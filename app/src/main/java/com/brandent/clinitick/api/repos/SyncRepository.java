package com.brandent.clinitick.api.repos;

import androidx.lifecycle.MutableLiveData;

import com.brandent.clinitick.api.RetrofitService;
import com.brandent.clinitick.api.SyncApi;
import com.brandent.clinitick.api.models.sync.SyncRequest;
import com.brandent.clinitick.api.models.sync.SyncResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncRepository {
    private static SyncRepository syncRepository;
    private final SyncApi syncApi;

    public SyncRepository() {
        syncApi = RetrofitService.createService(SyncApi.class);
    }

    public static SyncRepository getInstance() {
        if (syncRepository == null) {
            syncRepository = new SyncRepository();
        }
        return syncRepository;
    }

    public MutableLiveData<SyncResponse> sync(SyncRequest syncRequest, String token) {

        MutableLiveData<SyncResponse> syncResponse = new MutableLiveData<>();

        syncApi.sync(token, syncRequest).enqueue(new Callback<SyncResponse>() {
            @Override
            public void onResponse(@NotNull Call<SyncResponse> call
                    , @NotNull Response<SyncResponse> response) {
                if (response.isSuccessful()) {
                    syncResponse.setValue(response.body());
                } else {
                    SyncResponse unsuccessful = new SyncResponse(null
                            , "Unsuccessful!", null, null, null
                            , null, null);
                    syncResponse.setValue(unsuccessful);
                }
            }

            @Override
            public void onFailure(@NotNull Call<SyncResponse> call, @NotNull Throwable t) {
                syncResponse.setValue(null);
            }
        });

        return syncResponse;
    }
}
