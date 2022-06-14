package com.gmail.kostia_borozdyh.dto;

public class PointDTO {
    private String longitude;
    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public PointDTO() {

    }

    public PointDTO(String latitude, String longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
