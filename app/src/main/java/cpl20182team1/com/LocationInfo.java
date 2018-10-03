package cpl20182team1.com;

import java.util.Date;

public class LocationInfo {

    String date;
    String time;
    String locationName;
    Double latitude;
    Double longitude;

    public LocationInfo(){

    }

    public LocationInfo(String date, String time, String locationName, Double latitude, double longitude){
        this.date = date;
        this.time = time;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
