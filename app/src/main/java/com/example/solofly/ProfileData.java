package com.example.solofly;

public class ProfileData {
    String name, email, phone, date_of_birth, time, profileUrl;

    public ProfileData() {
    }

    public ProfileData(String name, String email, String phone, String date_of_birth, String profileUrl, String time) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.date_of_birth = date_of_birth;
        this.profileUrl = profileUrl;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setImage(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
