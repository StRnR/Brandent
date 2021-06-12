package com.brandent.clinitick.models;

public class AddAppointmentModel {
    private String title;
    private Long price;
    private Long timeStamp;

    public AddAppointmentModel(String title, Long price, Long timeStamp) {
        this.title = title;
        this.price = price;
        this.timeStamp = timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
