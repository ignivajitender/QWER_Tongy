package com.igniva.qwer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        public int is_voicecall;
        public int is_videocall;
        public int is_push_notification;
        public List<UserImageData> user_image;
        public String about;
        private UserRoleBean user_role;

        public int isIs_voicecall() {
            return is_voicecall;
        }

        public void setIs_voicecall(int is_voicecall) {
            this.is_voicecall = is_voicecall;
        }

        public int isIs_videocall() {
            return is_videocall;
        }

        public void setIs_videocall(int is_videocall) {
            this.is_videocall = is_videocall;
        }

        public int isIs_push_notification() {
            return is_push_notification;
        }

        public void setIs_push_notification(int is_push_notification) {
            this.is_push_notification = is_push_notification;
        }

        public UserRoleBean getUser_role() {
            return user_role;
        }

        public void setUser_role(UserRoleBean user_role) {
            this.user_role = user_role;
        }

        public List<UserImageData> getUser_image() {
            return user_image;
        }

        public void setUser_image(ArrayList<UserImageData> user_image) {
            this.user_image = user_image;
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

    }

    public static class UserImageData implements Serializable {

        //        "user_image":[{"id":17,"user_id":"82","image":"1508997738-E7540774.png","is_cover_image":"0"},
//          {"id":16,"user_id":"82","image":"1508923747-07D68753.jpg","is_cover_image":"0"}]
        public String id;
        public String user_id;
        public String image;
        public String is_cover_image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getIs_cover_image() {
            return is_cover_image;
        }

        public void setIs_cover_image(String is_cover_image) {
            this.is_cover_image = is_cover_image;
        }

    }

    public static class UserRoleBean {
        /**
         * id : 2
         * name : user
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
