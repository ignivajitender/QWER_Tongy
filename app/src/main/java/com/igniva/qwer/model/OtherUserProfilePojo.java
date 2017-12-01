package com.igniva.qwer.model;

import java.util.List;

/**
 * Created by igniva-android13 on 22/11/17.
 */

public class OtherUserProfilePojo {
    /**
     * status : 200
     * description : Users fetch successfully.
     * users : {"id":59,"name":"User1","email":"user1@yopmail.com","secondary_email":"","password":"$2y$10$eXaTom.pgINMFIry1FqKDehKmUuEd/yo1IX2IQgmVRWzfKs5qaO1K","verification_code":"","forgot_password_token":"545O68479040317","is_verified":"1","gender":"Male","age":"100","city":"chandigarh","country":"india","pincode":"gwe 1236","lat":"0.000000","lng":"0.000000","about":"test about","platform":"app","auth_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjU5LCJpc3MiOiJodHRwOi8vdG9uZ3kuaWduaXZhc3RhZ2luZy5jb20vYXBpL2xvZ2luIiwiaWF0IjoxNTExMjU4NTIxLCJleHAiOjE1MTEzNDQ5MjEsIm5iZiI6MTUxMTI1ODUyMSwianRpIjoiMkJXSHlhMnREUzBsOTVjdiJ9.rKteqVPz-vIIB3dp52tJdEBcgKvpcnPqXng2Q6wFtQk","social_id":null,"device_id":"dv08JR59apg:APA91bGRHy7m9odGQLzEx-fYVl_mSCaJWyALotCY2vJ5FkRfpMltWOdQ0aMT9F0XFwsa-MJzTWqI4aSHxCVsi6l8KsjqEfPpud3AyMz-knPli9WIq_M4VqImOsFAF2griegKYqHJ84Ow","device_type":"android","status":"1","pref_status":"1","is_push_notification":"1","is_voicecall":"1","is_videocall":"0","created_at":"1507534320","updated_at":"1507534320","role_id":"2","user_image":[],"user_language":[{"id":32,"user_id":"59","language_id":"26","proficiency":"intermediate","type":"speak","languages":{"id":26,"name":"English"}},{"id":33,"user_id":"59","language_id":"74","proficiency":"intermediate","type":"speak","languages":{"id":74,"name":"French"}},{"id":34,"user_id":"59","language_id":"75","proficiency":"intermediate","type":"speak","languages":{"id":75,"name":"German"}},{"id":35,"user_id":"59","language_id":"76","proficiency":"intermediate","type":"learn","languages":{"id":76,"name":"Dutch"}},{"id":36,"user_id":"59","language_id":"77","proficiency":"intermediate","type":"learn","languages":{"id":77,"name":"Spanish"}},{"id":37,"user_id":"59","language_id":"78","proficiency":"intermediate","type":"learn","languages":{"id":78,"name":"Arabic"}}],"user_recieve":[],"user_send":[{"request_from":"98","request_to":"59","status":"1"}]}
     */

    private int status;
    private String description;
    private UsersPojo users;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UsersPojo getUsers() {
        return users;
    }

    public void setUsers(UsersPojo users) {
        this.users = users;
    }

    public static class UsersPojo {
        /**
         * id : 59
         * name : User1
         * email : user1@yopmail.com
         * secondary_email :
         * password : $2y$10$eXaTom.pgINMFIry1FqKDehKmUuEd/yo1IX2IQgmVRWzfKs5qaO1K
         * verification_code :
         * forgot_password_token : 545O68479040317
         * is_verified : 1
         * gender : Male
         * age : 100
         * city : chandigarh
         * country : india
         * pincode : gwe 1236
         * lat : 0.000000
         * lng : 0.000000
         * about : test about
         * platform : app
         * auth_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjU5LCJpc3MiOiJodHRwOi8vdG9uZ3kuaWduaXZhc3RhZ2luZy5jb20vYXBpL2xvZ2luIiwiaWF0IjoxNTExMjU4NTIxLCJleHAiOjE1MTEzNDQ5MjEsIm5iZiI6MTUxMTI1ODUyMSwianRpIjoiMkJXSHlhMnREUzBsOTVjdiJ9.rKteqVPz-vIIB3dp52tJdEBcgKvpcnPqXng2Q6wFtQk
         * social_id : null
         * device_id : dv08JR59apg:APA91bGRHy7m9odGQLzEx-fYVl_mSCaJWyALotCY2vJ5FkRfpMltWOdQ0aMT9F0XFwsa-MJzTWqI4aSHxCVsi6l8KsjqEfPpud3AyMz-knPli9WIq_M4VqImOsFAF2griegKYqHJ84Ow
         * device_type : android
         * status : 1
         * pref_status : 1
         * is_push_notification : 1
         * is_voicecall : 1
         * is_videocall : 0
         * created_at : 1507534320
         * updated_at : 1507534320
         * role_id : 2
         * user_image : []
         * user_language : [{"id":32,"user_id":"59","language_id":"26","proficiency":"intermediate","type":"speak","languages":{"id":26,"name":"English"}},{"id":33,"user_id":"59","language_id":"74","proficiency":"intermediate","type":"speak","languages":{"id":74,"name":"French"}},{"id":34,"user_id":"59","language_id":"75","proficiency":"intermediate","type":"speak","languages":{"id":75,"name":"German"}},{"id":35,"user_id":"59","language_id":"76","proficiency":"intermediate","type":"learn","languages":{"id":76,"name":"Dutch"}},{"id":36,"user_id":"59","language_id":"77","proficiency":"intermediate","type":"learn","languages":{"id":77,"name":"Spanish"}},{"id":37,"user_id":"59","language_id":"78","proficiency":"intermediate","type":"learn","languages":{"id":78,"name":"Arabic"}}]
         * user_recieve : []
         * user_send : [{"request_from":"98","request_to":"59","status":"1"}]
         */

        private int id;
        private String name;
        private String email;
        private String secondary_email;
        private String password;
        private String verification_code;
        private String forgot_password_token;
        private String is_verified;
        private String gender;
        private String age;
        private String city;
        private String country;
        private String pincode;
        private String lat;
        private String lng;
        private String about;
        private String platform;
        private String auth_token;
        private Object social_id;
        private String device_id;
        private String device_type;
        private String status;
        private String pref_status;
        private int is_push_notification;
        private int is_voicecall;
        private int is_videocall;
        private String created_at;
        private String updated_at;
        private String role_id;

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String distance;

        public ConnectionPojo.ConnectionDataPojo.UserCountryPojo getUser_country() {
            return user_country;
        }

        public void setUser_country(ConnectionPojo.ConnectionDataPojo.UserCountryPojo user_country) {
            this.user_country = user_country;
        }

        public ConnectionPojo.ConnectionDataPojo.UserCountryPojo user_country;

        public List<UserBlockPojo> getUser_block() {
            return user_block;
        }

        public void setUser_block(List<UserBlockPojo> user_block) {
            this.user_block = user_block;
        }

        public List<UserBlockPojo> user_block;

        private List<ConnectionPojo.ConnectionDataPojo.UserImagePojo> user_image;

        public List<UserSpeakPojo> getUser_speak() {
            return user_speak;
        }

        public void setUser_speak(List<UserSpeakPojo> user_speak) {
            this.user_speak = user_speak;
        }

        private List<UserSpeakPojo> user_speak;

        public List<UserSpeakPojo> getUser_learn() {
            return user_learn;
        }

        public void setUser_learn(List<UserSpeakPojo> user_learn) {
            this.user_learn = user_learn;
        }

        private List<UserSpeakPojo> user_learn;
        //private List<?> user_recieve;
        private List<UserSendPojo> user_send;

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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getVerification_code() {
            return verification_code;
        }

        public void setVerification_code(String verification_code) {
            this.verification_code = verification_code;
        }

        public String getForgot_password_token() {
            return forgot_password_token;
        }

        public void setForgot_password_token(String forgot_password_token) {
            this.forgot_password_token = forgot_password_token;
        }

        public String getIs_verified() {
            return is_verified;
        }

        public void setIs_verified(String is_verified) {
            this.is_verified = is_verified;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
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

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getAuth_token() {
            return auth_token;
        }

        public void setAuth_token(String auth_token) {
            this.auth_token = auth_token;
        }

        public Object getSocial_id() {
            return social_id;
        }

        public void setSocial_id(Object social_id) {
            this.social_id = social_id;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPref_status() {
            return pref_status;
        }

        public void setPref_status(String pref_status) {
            this.pref_status = pref_status;
        }

        public int getIs_push_notification() {
            return is_push_notification;
        }

        public void setIs_push_notification(int is_push_notification) {
            this.is_push_notification = is_push_notification;
        }

        public int getIs_voicecall() {
            return is_voicecall;
        }

        public void setIs_voicecall(int is_voicecall) {
            this.is_voicecall = is_voicecall;
        }

        public int getIs_videocall() {
            return is_videocall;
        }

        public void setIs_videocall(int is_videocall) {
            this.is_videocall = is_videocall;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public List<ConnectionPojo.ConnectionDataPojo.UserImagePojo> getUser_image() {
            return user_image;
        }

        public void setUser_image(List<ConnectionPojo.ConnectionDataPojo.UserImagePojo> user_image) {
            this.user_image = user_image;
        }


       /* public List<?> getUser_recieve() {
            return user_recieve;
        }

        public void setUser_recieve(List<?> user_recieve) {
            this.user_recieve = user_recieve;
        }*/

        public List<UserSendPojo> getUser_send() {
            return user_send;
        }

        public void setUser_send(List<UserSendPojo> user_send) {
            this.user_send = user_send;
        }

        public static class UserSpeakPojo {
            /**
             * id : 32
             * user_id : 59
             * language_id : 26
             * proficiency : intermediate
             * type : speak
             * languages : {"id":26,"name":"English"}
             */

            private int id;
            private String user_id;
            private String language_id;
            private String proficiency;
            private String type;
            private LanguagesPojo languages;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getLanguage_id() {
                return language_id;
            }

            public void setLanguage_id(String language_id) {
                this.language_id = language_id;
            }

            public String getProficiency() {
                return proficiency;
            }

            public void setProficiency(String proficiency) {
                this.proficiency = proficiency;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public LanguagesPojo getLanguages() {
                return languages;
            }

            public void setLanguages(LanguagesPojo languages) {
                this.languages = languages;
            }

            public static class LanguagesPojo {
                /**
                 * id : 26
                 * name : English
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

        public static class UserSendPojo {
            /**
             * request_from : 98
             * request_to : 59
             * status : 1
             */

            public int request_from;
            public int request_to;
            public int status;

            public int getRequest_from() {
                return request_from;
            }

            public void setRequest_from(int request_from) {
                this.request_from = request_from;
            }

            public int getRequest_to() {
                return request_to;
            }

            public void setRequest_to(int request_to) {
                this.request_to = request_to;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }

    public static class UserBlockPojo {
        String blocked_to;

        public String getBlocked_to() {
            return blocked_to;
        }

        public void setBlocked_to(String blocked_to) {
            this.blocked_to = blocked_to;
        }

        public String getBlocked_by() {
            return blocked_by;
        }

        public void setBlocked_by(String blocked_by) {
            this.blocked_by = blocked_by;
        }

        String blocked_by;

    }
}
