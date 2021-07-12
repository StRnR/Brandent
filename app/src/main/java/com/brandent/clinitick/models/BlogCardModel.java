package com.brandent.clinitick.models;

public class BlogCardModel {
    private int id;
    private String title;
    private String source_url;

    public BlogCardModel(int id, String title, String source_url) {
        this.id = id;
        this.title = title;
        this.source_url = source_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }


}
