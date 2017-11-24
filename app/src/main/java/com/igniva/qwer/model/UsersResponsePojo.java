package com.igniva.qwer.model;

import java.util.List;

/**
 * Created by igniva-android13 on 24/11/17.
 */

public class UsersResponsePojo {
    /**
     * status : 200
     * description : Users fetch successfully.
     * users : {"current_page":1,"data":[{"id":110,"name":"Amit","email":"amit@yopmail.com","secondary_email":null,"password":"$2y$10$j8j.E0hP7M./PpwWPz/QMOYfBkUOFkNVYHdV.nKe3CD.yrtNSg8hC","verification_code":"","forgot_password_token":null,"is_verified":"1","gender":"Male","age":"35","city":"Panchkula","country":"India","country_id":"0","pincode":"178900qqaaa","lat":"30.640749","lng":"75.860596","about":"nautica america","platform":"app","auth_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjExMCwiaXNzIjoiaHR0cDovL3Rvbmd5Lmlnbml2YXN0YWdpbmcuY29tL2FwaS9sb2dpbiIsImlhdCI6MTUxMDA1Nzk1OSwiZXhwIjoxNTEwMTQ0MzU5LCJuYmYiOjE1MTAwNTc5NTksImp0aSI6IkhLdXNudnhaclZ2RHdId0sifQ.uO5QYNzhSDRwZ6i70yYMjJohIAWl_pilCt3MV975dNM","social_id":null,"device_id":"abcd123456fafaaqqqaaaqqq","device_type":"android","status":"1","pref_status":"1","is_push_notification":"1","is_voicecall":"1","is_videocall":"1","created_at":"1510041537","updated_at":"1510041537","role_id":"2","user_image":[{"user_id":"110","image":"http://tongy.ignivastaging.com/images/profile_image/1510055417-5B122607.jpg","is_cover_image":"0"}],"user_speak":[{"id":291,"user_id":"110","language_id":"74","proficiency":"begineer","type":"speak","languages":{"id":74,"name":"French"}},{"id":292,"user_id":"110","language_id":"75","proficiency":"intermediate","type":"speak","languages":{"id":75,"name":"German"}},{"id":293,"user_id":"110","language_id":"76","proficiency":"professional","type":"speak","languages":{"id":76,"name":"Dutch"}}],"user_learn":[{"id":294,"user_id":"110","language_id":"77","proficiency":"professional","type":"learn","languages":{"id":77,"name":"Spanish"}},{"id":295,"user_id":"110","language_id":"78","proficiency":"begineer","type":"learn","languages":{"id":78,"name":"Arabic"}},{"id":296,"user_id":"110","language_id":"79","proficiency":"professional","type":"learn","languages":{"id":79,"name":"Greek"}}],"user_recieve":[],"user_send":[]},{"id":90,"name":"Nancy","email":"nancy@yopmail.com","secondary_email":null,"password":"$2y$10$sBmJbeoqyxoXWEZVhRh.BuBhjJ8Jrxqd5kugMg4kcbsCSH.arxUFe","verification_code":"","forgot_password_token":"","is_verified":"1","gender":"Male","age":"25","city":"punjab","country":"India","country_id":"0","pincode":"160055","lat":"0.000000","lng":"0.000000","about":"nicely done","platform":"app","auth_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjkwLCJpc3MiOiJodHRwOi8vdG9uZ3kuaWduaXZhc3RhZ2luZy5jb20vYXBpL2xvZ2luIiwiaWF0IjoxNTExNDI4NTAxLCJleHAiOjE1MTE1MTQ5MDEsIm5iZiI6MTUxMTQyODUwMSwianRpIjoiQTRyNlF2QXhlQXQxTDZ2RyJ9.N9V4378zTyrDUABgSRtxZ7KkE3UJA6HQ1aWIiS3l8nQ","social_id":null,"device_id":"1234567890","device_type":"android","status":"1","pref_status":"1","is_push_notification":"1","is_voicecall":"1","is_videocall":"1","created_at":"1509088783","updated_at":"1509088783","role_id":"2","user_image":[],"user_speak":[{"id":367,"user_id":"90","language_id":"26","proficiency":"INTERMEDIATE","type":"speak","languages":{"id":26,"name":"English"}}],"user_learn":[{"id":368,"user_id":"90","language_id":"77","proficiency":"BEGINNER","type":"learn","languages":{"id":77,"name":"Spanish"}}],"user_recieve":[],"user_send":[]}]}
     */

    public int status;
    public String description;
    public UsersPojo users;

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
         * current_page : 1
         * data : [{"id":110,"name":"Amit","email":"amit@yopmail.com","secondary_email":null,"password":"$2y$10$j8j.E0hP7M./PpwWPz/QMOYfBkUOFkNVYHdV.nKe3CD.yrtNSg8hC","verification_code":"","forgot_password_token":null,"is_verified":"1","gender":"Male","age":"35","city":"Panchkula","country":"India","country_id":"0","pincode":"178900qqaaa","lat":"30.640749","lng":"75.860596","about":"nautica america","platform":"app","auth_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjExMCwiaXNzIjoiaHR0cDovL3Rvbmd5Lmlnbml2YXN0YWdpbmcuY29tL2FwaS9sb2dpbiIsImlhdCI6MTUxMDA1Nzk1OSwiZXhwIjoxNTEwMTQ0MzU5LCJuYmYiOjE1MTAwNTc5NTksImp0aSI6IkhLdXNudnhaclZ2RHdId0sifQ.uO5QYNzhSDRwZ6i70yYMjJohIAWl_pilCt3MV975dNM","social_id":null,"device_id":"abcd123456fafaaqqqaaaqqq","device_type":"android","status":"1","pref_status":"1","is_push_notification":"1","is_voicecall":"1","is_videocall":"1","created_at":"1510041537","updated_at":"1510041537","role_id":"2","user_image":[{"user_id":"110","image":"http://tongy.ignivastaging.com/images/profile_image/1510055417-5B122607.jpg","is_cover_image":"0"}],"user_speak":[{"id":291,"user_id":"110","language_id":"74","proficiency":"begineer","type":"speak","languages":{"id":74,"name":"French"}},{"id":292,"user_id":"110","language_id":"75","proficiency":"intermediate","type":"speak","languages":{"id":75,"name":"German"}},{"id":293,"user_id":"110","language_id":"76","proficiency":"professional","type":"speak","languages":{"id":76,"name":"Dutch"}}],"user_learn":[{"id":294,"user_id":"110","language_id":"77","proficiency":"professional","type":"learn","languages":{"id":77,"name":"Spanish"}},{"id":295,"user_id":"110","language_id":"78","proficiency":"begineer","type":"learn","languages":{"id":78,"name":"Arabic"}},{"id":296,"user_id":"110","language_id":"79","proficiency":"professional","type":"learn","languages":{"id":79,"name":"Greek"}}],"user_recieve":[],"user_send":[]},{"id":90,"name":"Nancy","email":"nancy@yopmail.com","secondary_email":null,"password":"$2y$10$sBmJbeoqyxoXWEZVhRh.BuBhjJ8Jrxqd5kugMg4kcbsCSH.arxUFe","verification_code":"","forgot_password_token":"","is_verified":"1","gender":"Male","age":"25","city":"punjab","country":"India","country_id":"0","pincode":"160055","lat":"0.000000","lng":"0.000000","about":"nicely done","platform":"app","auth_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjkwLCJpc3MiOiJodHRwOi8vdG9uZ3kuaWduaXZhc3RhZ2luZy5jb20vYXBpL2xvZ2luIiwiaWF0IjoxNTExNDI4NTAxLCJleHAiOjE1MTE1MTQ5MDEsIm5iZiI6MTUxMTQyODUwMSwianRpIjoiQTRyNlF2QXhlQXQxTDZ2RyJ9.N9V4378zTyrDUABgSRtxZ7KkE3UJA6HQ1aWIiS3l8nQ","social_id":null,"device_id":"1234567890","device_type":"android","status":"1","pref_status":"1","is_push_notification":"1","is_voicecall":"1","is_videocall":"1","created_at":"1509088783","updated_at":"1509088783","role_id":"2","user_image":[],"user_speak":[{"id":367,"user_id":"90","language_id":"26","proficiency":"INTERMEDIATE","type":"speak","languages":{"id":26,"name":"English"}}],"user_learn":[{"id":368,"user_id":"90","language_id":"77","proficiency":"BEGINNER","type":"learn","languages":{"id":77,"name":"Spanish"}}],"user_recieve":[],"user_send":[]}]
         */

        public int current_page;
        public List<UserDataPojo> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public List<UserDataPojo> getData() {
            return data;
        }

        public void setData(List<UserDataPojo> data) {
            this.data = data;
        }

        public static class UserDataPojo {
            /**
             * id : 110
             * name : Amit
             * email : amit@yopmail.com
             * secondary_email : null
             * password : $2y$10$j8j.E0hP7M./PpwWPz/QMOYfBkUOFkNVYHdV.nKe3CD.yrtNSg8hC
             * verification_code :
             * forgot_password_token : null
             * is_verified : 1
             * gender : Male
             * age : 35
             * city : Panchkula
             * country : India
             * country_id : 0
             * pincode : 178900qqaaa
             * lat : 30.640749
             * lng : 75.860596
             * about : nautica america
             * platform : app
             * auth_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjExMCwiaXNzIjoiaHR0cDovL3Rvbmd5Lmlnbml2YXN0YWdpbmcuY29tL2FwaS9sb2dpbiIsImlhdCI6MTUxMDA1Nzk1OSwiZXhwIjoxNTEwMTQ0MzU5LCJuYmYiOjE1MTAwNTc5NTksImp0aSI6IkhLdXNudnhaclZ2RHdId0sifQ.uO5QYNzhSDRwZ6i70yYMjJohIAWl_pilCt3MV975dNM
             * social_id : null
             * device_id : abcd123456fafaaqqqaaaqqq
             * device_type : android
             * status : 1
             * pref_status : 1
             * is_push_notification : 1
             * is_voicecall : 1
             * is_videocall : 1
             * created_at : 1510041537
             * updated_at : 1510041537
             * role_id : 2
             * user_image : [{"user_id":"110","image":"http://tongy.ignivastaging.com/images/profile_image/1510055417-5B122607.jpg","is_cover_image":"0"}]
             * user_speak : [{"id":291,"user_id":"110","language_id":"74","proficiency":"begineer","type":"speak","languages":{"id":74,"name":"French"}},{"id":292,"user_id":"110","language_id":"75","proficiency":"intermediate","type":"speak","languages":{"id":75,"name":"German"}},{"id":293,"user_id":"110","language_id":"76","proficiency":"professional","type":"speak","languages":{"id":76,"name":"Dutch"}}]
             * user_learn : [{"id":294,"user_id":"110","language_id":"77","proficiency":"professional","type":"learn","languages":{"id":77,"name":"Spanish"}},{"id":295,"user_id":"110","language_id":"78","proficiency":"begineer","type":"learn","languages":{"id":78,"name":"Arabic"}},{"id":296,"user_id":"110","language_id":"79","proficiency":"professional","type":"learn","languages":{"id":79,"name":"Greek"}}]
             * user_recieve : []
             * user_send : []
             */

            public int id;
            public String name;
            public String email;
            public Object secondary_email;
            public String password;
            public String verification_code;
            public Object forgot_password_token;
            public String is_verified;
            public String gender;
            public String age;
            public String city;
            public String country;
            public String country_id;
            public String pincode;
            public String lat;
            public String lng;
            public String about;
            public String platform;
            public String auth_token;
            public Object social_id;
            public String device_id;
            public String device_type;
            public String status;
            public String pref_status;
            public String is_push_notification;
            public String is_voicecall;
            public String is_videocall;
            public String created_at;
            public String updated_at;
            public String role_id;
            public List<ConnectionPojo.ConnectionDataPojo.UserImagePojo> user_image;
            public List<OtherUserProfilePojo.UsersPojo.UserSpeakPojo> user_speak;
            public List<OtherUserProfilePojo.UsersPojo.UserSpeakPojo> user_learn;

            public List<UserRecievePojo> getUser_recieve() {
                return user_recieve;
            }

            public void setUser_recieve(List<UserRecievePojo> user_recieve) {
                this.user_recieve = user_recieve;
            }

            public List<UserRecievePojo> user_recieve;
            public List<OtherUserProfilePojo.UsersPojo.UserSendPojo> user_send;

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

            public Object getSecondary_email() {
                return secondary_email;
            }

            public void setSecondary_email(Object secondary_email) {
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

            public Object getForgot_password_token() {
                return forgot_password_token;
            }

            public void setForgot_password_token(Object forgot_password_token) {
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

            public String getCountry_id() {
                return country_id;
            }

            public void setCountry_id(String country_id) {
                this.country_id = country_id;
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

            public String getIs_push_notification() {
                return is_push_notification;
            }

            public void setIs_push_notification(String is_push_notification) {
                this.is_push_notification = is_push_notification;
            }

            public String getIs_voicecall() {
                return is_voicecall;
            }

            public void setIs_voicecall(String is_voicecall) {
                this.is_voicecall = is_voicecall;
            }

            public String getIs_videocall() {
                return is_videocall;
            }

            public void setIs_videocall(String is_videocall) {
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

            public List<OtherUserProfilePojo.UsersPojo.UserSpeakPojo> getUser_speak() {
                return user_speak;
            }

            public void setUser_speak(List<OtherUserProfilePojo.UsersPojo.UserSpeakPojo> user_speak) {
                this.user_speak = user_speak;
            }

            public List<OtherUserProfilePojo.UsersPojo.UserSpeakPojo> getUser_learn() {
                return user_learn;
            }

            public void setUser_learn(List<OtherUserProfilePojo.UsersPojo.UserSpeakPojo> user_learn) {
                this.user_learn = user_learn;
            }
/*
            public List<?> getUser_recieve() {
                return user_recieve;
            }

            public void setUser_recieve(List<?> user_recieve) {
                this.user_recieve = user_recieve;
            }*/

            public List<OtherUserProfilePojo.UsersPojo.UserSendPojo> getUser_send() {
                return user_send;
            }

            public void setUser_send(List<OtherUserProfilePojo.UsersPojo.UserSendPojo> user_send) {
                this.user_send = user_send;
            }

            public static class UserImagePojo {
                /**
                 * user_id : 110
                 * image : http://tongy.ignivastaging.com/images/profile_image/1510055417-5B122607.jpg
                 * is_cover_image : 0
                 */

                public String user_id;
                public String image;
                public String is_cover_image;

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

            public static class UserSpeakPojo {
                /**
                 * id : 291
                 * user_id : 110
                 * language_id : 74
                 * proficiency : begineer
                 * type : speak
                 * languages : {"id":74,"name":"French"}
                 */

                public int id;
                public String user_id;
                public String language_id;
                public String proficiency;
                public String type;
                public LanguagesPojo languages;

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
                     * id : 74
                     * name : French
                     */

                    public int id;
                    public String name;

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

            public static class UserLearnPojo {
                /**
                 * id : 294
                 * user_id : 110
                 * language_id : 77
                 * proficiency : professional
                 * type : learn
                 * languages : {"id":77,"name":"Spanish"}
                 */

                public int id;
                public String user_id;
                public String language_id;
                public String proficiency;
                public String type;
                public LanguagesPojo languages;

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
                     * id : 77
                     * name : Spanish
                     */

                    public int id;
                    public String name;

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
        }
    }

    public static class UserRecievePojo {

       /* "request_from": "59",
                "request_to": "110",
                "status": "0"*/

       int request_from;
        int request_to;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        String status;
    }
}
