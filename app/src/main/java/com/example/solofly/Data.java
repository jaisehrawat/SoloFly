package com.example.solofly;
import android.widget.ImageView;

public class Data {

        public String name, email, phone, date_of_birth, profileUrl;

        public Data() {

        }

        public Data(String name, String email, String phone, String date_of_birth, String profileUrl) {
                this.name = name;
                this.email = email;
                this.phone = phone;
                this.date_of_birth = date_of_birth;
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

        public void setProfileUrl(String profile) {
                this.profileUrl = profile;
        }
}
