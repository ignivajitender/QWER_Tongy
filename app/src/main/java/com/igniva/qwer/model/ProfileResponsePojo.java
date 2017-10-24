package com.igniva.qwer.model;

/**
 * Wrapper class -  to wrap ant response from webservice
 */
public class ProfileResponsePojo {

//    {
//        "status": 200,
//            "description": "User profile fetch successfully.",
//            "data": {
//        "id": 82,
//                "name": "test1",
//                "email": "test1@yopmail.com",
//                "secondary_email": null,
//                "password": "$2y$10$2K9CDSx7w6FrItx9qByn6.na2BqV2jMieU2Nsna9we9b55byRfsle",
//                "verification_code": "",
//                "forgot_password_token": null,
//                "is_verified": "1",
//                "gender": null,
//                "age": null,
//                "city": null,
//                "country": null,
//                "pincode": null,
//                "about": null,
//                "platform": "app",
//                "auth_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjgyLCJpc3MiOiJodHRwOi8vdG9uZ3kuaWduaXZhc3RhZ2luZy5jb20vYXBpL2xvZ2luIiwiaWF0IjoxNTA4ODQ3NDE3LCJleHAiOjE1MDg5MzM4MTcsIm5iZiI6MTUwODg0NzQxNywianRpIjoidDhaaGVua2tjRG9aQXpNcCJ9.GpItcI6NjRQz5BA-KInbUkSf2doV3vbDC8_J6qoUJHI",
//                "social_id": null,
//                "status": "1",
//                "is_push_notification": "1",
//                "is_voicecall": "1",
//                "is_videocall": "1",
//                "created_at": "1508819591",
//                "updated_at": "1508819591",
//                "role_id": "2",
//                "user_role": {
//            "id": 2,
//                    "name": "user"
//        },
//        "user_image": []
//    }
//    }
//


    private int status;
    private String description;
    private ProfileDataPojo data;
    private ErrorPojo error;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ProfileDataPojo getData() {
        return data;
    }

    public void setData(ProfileDataPojo data) {
        this.data = data;
    }

    public ErrorPojo getError() {
        return error;
    }

    public void setError(ErrorPojo error) {
        this.error = error;
    }

    public static class ProfileDataPojo {

       public String name;
        public String email;
        public String secondary_email;
        public String gender;
        public String city;
        public String age;
        public String country;
        public String pincode;

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

        public String getSecondary_email() {
            return secondary_email;
        }

        public void setSecondary_email(String secondary_email) {
            this.secondary_email = secondary_email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String about;

    }
}
