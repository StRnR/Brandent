package com.brandent.clinitick.api.models.blog;

public class Sizes {
    private Medium medium;
    private Large large;
    private Thumbnail thumbnail;
    private MediumLarge medium_large;
    private _1536x1536 _1536x1536;
    private _2048x2048 _2048x2048;
    private Full full;

    public Sizes(Medium medium, Large large, Thumbnail thumbnail, MediumLarge medium_large
            , com.brandent.clinitick.api.models.blog._1536x1536 _1536x1536
            , com.brandent.clinitick.api.models.blog._2048x2048 _2048x2048, Full full) {
        this.medium = medium;
        this.large = large;
        this.thumbnail = thumbnail;
        this.medium_large = medium_large;
        this._1536x1536 = _1536x1536;
        this._2048x2048 = _2048x2048;
        this.full = full;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Large getLarge() {
        return large;
    }

    public void setLarge(Large large) {
        this.large = large;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public MediumLarge getMedium_large() {
        return medium_large;
    }

    public void setMedium_large(MediumLarge medium_large) {
        this.medium_large = medium_large;
    }

    public com.brandent.clinitick.api.models.blog._1536x1536 get_1536x1536() {
        return _1536x1536;
    }

    public void set_1536x1536(com.brandent.clinitick.api.models.blog._1536x1536 _1536x1536) {
        this._1536x1536 = _1536x1536;
    }

    public com.brandent.clinitick.api.models.blog._2048x2048 get_2048x2048() {
        return _2048x2048;
    }

    public void set_2048x2048(com.brandent.clinitick.api.models.blog._2048x2048 _2048x2048) {
        this._2048x2048 = _2048x2048;
    }

    public Full getFull() {
        return full;
    }

    public void setFull(Full full) {
        this.full = full;
    }
}
