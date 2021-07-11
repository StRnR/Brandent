package com.brandent.clinitick.api.models.blog;

public class WpTerm {
    private String taxonomy;
    private boolean embeddable;
    private String href;

    public WpTerm(String taxonomy, boolean embeddable, String href) {
        this.taxonomy = taxonomy;
        this.embeddable = embeddable;
        this.href = href;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
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
