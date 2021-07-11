package com.brandent.clinitick.api.models.blog;

public class WpFeaturedmedia {
    private boolean embeddable;
    private String href;

    public WpFeaturedmedia(boolean embeddable, String href) {
        this.embeddable = embeddable;
        this.href = href;
    }

    public boolean isEmbeddable() {
        return embeddable;
    }

    public void setEmbeddable(boolean embeddable) {
        this.embeddable = embeddable;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
