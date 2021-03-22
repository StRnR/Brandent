package com.pixium.clinitick.api.models.auth;

public class PhoneRequest {
    private String phone;

    public PhoneRequest(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
