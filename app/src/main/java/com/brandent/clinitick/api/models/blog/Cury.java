package com.brandent.clinitick.api.models.blog;

public class Cury {
    private String name;
    private String href;
    private boolean templated;

    public Cury(String name, String href, boolean templated) {
        this.name = name;
        this.href = href;
        this.templated = templated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isTemplated() {
        return templated;
    }

    public void setTemplated(boolean templated) {
        this.templated = templated;
    }
}
