package com.brandent.clinitick.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brandent.clinitick.R;
import com.brandent.clinitick.models.BlogCardModel;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogHolder> {
    private List<BlogCardModel> blogCardModels = new ArrayList<>();

    @NotNull
    @Override
    public BlogHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blog_post, parent, false);
        return new BlogHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BlogHolder holder, int position) {
        BlogCardModel current = blogCardModels.get(position);
        holder.setIsRecyclable(false);
        holder.textView.setText(current.getTitle());
        if (position != RecyclerView.NO_POSITION && !Objects.isNull(current)) {
            Glide.with(holder.imageView.getContext()).load(current.getSource_url())
                    .into(holder.imageView);
        } else {
            Glide.with(holder.imageView.getContext()).clear(holder.imageView);
            holder.imageView.setImageDrawable(null);
        }

    }

    @Override
    public int getItemCount() {
        return blogCardModels.size();
    }

    public void setBlogCardModels(List<BlogCardModel> blogCardModels) {
        this.blogCardModels = blogCardModels;
        notifyDataSetChanged();
    }

    public List<BlogCardModel> getBlogCardModels() {
        return blogCardModels;
    }

    class BlogHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        public BlogHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_blog_post_thumbnail);
            textView = itemView.findViewById(R.id.tv_blog_post_title);
        }
    }

}
