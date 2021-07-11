package com.brandent.clinitick.api.models.blog;

public class MediaDetails {
    private int width;
    private int height;
    private String file;
    private Sizes sizes;
    private ImageMeta image_meta;

    public MediaDetails(int width, int height, String file, Sizes sizes, ImageMeta image_meta) {
        this.width = width;
        this.height = height;
        this.file = file;
        this.sizes = sizes;
        this.image_meta = image_meta;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Sizes getSizes() {
        return sizes;
    }

    public void setSizes(Sizes sizes) {
        this.sizes = sizes;
    }

    public ImageMeta getImage_meta() {
        return image_meta;
    }

    public void setImage_meta(ImageMeta image_meta) {
        this.image_meta = image_meta;
    }
}
