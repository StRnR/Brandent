package com.brandent.clinitick.api.models.blog;

public class Guid {
    private String rendered;

    public Guid(String rendered) {
        this.rendered = rendered;
    }

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }
}
