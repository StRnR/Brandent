package com.brandent.clinitick.api.repos;

import androidx.lifecycle.MutableLiveData;

import com.brandent.clinitick.api.BlogApi;
import com.brandent.clinitick.api.BlogService;
import com.brandent.clinitick.api.models.blog.Post;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogRepository {
    private static BlogRepository blogRepository;
    private final BlogApi blogApi;

    public BlogRepository() {
        blogApi = BlogService.createService(BlogApi.class);
    }

    public static BlogRepository getInstance() {
        if (blogRepository == null) {
            blogRepository = new BlogRepository();
        }
        return blogRepository;
    }

    public MutableLiveData<List<Post>> getPosts() {
        MutableLiveData<List<Post>> posts = new MutableLiveData<>();
        blogApi.getBolgPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NotNull Call<List<Post>> call
                    , @NotNull Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    posts.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Post>> call, @NotNull Throwable t) {
                posts.setValue(null);
            }
        });
        return posts;
    }
}
