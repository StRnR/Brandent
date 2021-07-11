package com.brandent.clinitick.api.models.blog;

public class PredecessorVersion {
    private int id;
    private String href;

    public PredecessorVersion(int id, String href) {
        this.id = id;
        this.href = href;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
