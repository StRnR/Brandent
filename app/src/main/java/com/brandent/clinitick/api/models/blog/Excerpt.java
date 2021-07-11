package com.brandent.clinitick.api.models.blog;

public class Excerpt {
    private String rendered;

    public Excerpt(String rendered) {
        this.rendered = rendered;
    }

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }
}
