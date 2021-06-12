package com.brandent.clinitick.api.models.auth;

public class CodeRequest {
    private String phone;

    private String code;

    public CodeRequest(String phone, String code) {
        this.phone = phone;
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
