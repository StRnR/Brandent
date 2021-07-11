package com.brandent.clinitick.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brandent.clinitick.R;
import com.brandent.clinitick.api.models.blog.Post;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogHolder> {
    private List<Post> posts = new ArrayList<>();

    @NotNull
    @Override
    public BlogHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blog_post, parent, false);
        return new BlogHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BlogHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class BlogHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        public BlogHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_blog_post_thumbnail);
            textView = (TextView) itemView.findViewById(R.id.tv_blog_post_title);
        }
    }

}
