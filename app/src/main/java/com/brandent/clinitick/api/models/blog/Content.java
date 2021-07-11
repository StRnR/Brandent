package com.brandent.clinitick.api.models.blog;

public class Content {
    private String rendered;

    public Content(String rendered) {
        this.rendered = rendered;
    }

    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }
}
