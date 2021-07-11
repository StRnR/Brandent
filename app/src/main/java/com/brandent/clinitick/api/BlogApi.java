package com.brandent.clinitick.api;

import com.brandent.clinitick.api.models.blog.Media;
import com.brandent.clinitick.api.models.blog.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BlogApi {
    @GET("/wp/v2/posts")
    Call<List<Post>> getBolgPosts();

    @GET("/wp/v2/media/{id}")
    Call<Media> getMedia(@Path("id") String id);
}
