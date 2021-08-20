package com.brandent.clinitick.api.models.auth;

public class DentistGetImageResponse {
    private String image;
    private String message;

    public DentistGetImageResponse(String image, String message) {
        this.image = image;
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
