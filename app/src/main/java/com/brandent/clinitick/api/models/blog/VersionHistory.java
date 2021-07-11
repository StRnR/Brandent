package com.brandent.clinitick.api.models.blog;

public class VersionHistory {
    private int count;
    private String href;

    public VersionHistory(int count, String href) {
        this.count = count;
        this.href = href;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
