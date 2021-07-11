package com.brandent.clinitick.api.models.blog;

import java.util.List;

public class ImageMeta {
    private String aperture;
    private String credit;
    private String camera;
    private String caption;
    private String created_timestamp;
    private String copyright;
    private String focal_length;
    private String iso;
    private String shutter_speed;
    private String title;
    private String orientation;
    private List<Object> keywords;

    public ImageMeta(String aperture, String credit, String camera, String caption
            , String created_timestamp, String copyright, String focal_length, String iso
            , String shutter_speed, String title, String orientation, List<Object> keywords) {
        this.aperture = aperture;
        this.credit = credit;
        this.camera = camera;
        this.caption = caption;
        this.created_timestamp = created_timestamp;
        this.copyright = copyright;
        this.focal_length = focal_length;
        this.iso = iso;
        this.shutter_speed = shutter_speed;
        this.title = title;
        this.orientation = orientation;
        this.keywords = keywords;
    }

    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCreated_timestamp() {
        return created_timestamp;
    }

    public void setCreated_timestamp(String created_timestamp) {
        this.created_timestamp = created_timestamp;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getFocal_length() {
        return focal_length;
    }

    public void setFocal_length(String focal_length) {
        this.focal_length = focal_length;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getShutter_speed() {
        return shutter_speed;
    }

    public void setShutter_speed(String shutter_speed) {
        this.shutter_speed = shutter_speed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public List<Object> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Object> keywords) {
        this.keywords = keywords;
    }
}
