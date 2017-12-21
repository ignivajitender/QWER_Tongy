package com.igniva.qwer.model;

import java.util.List;

/**
 * Created by igniva-android13 on 20/11/17.
 */

public class ConnectionPojo {
    /**
     * status : 200
     * description : Friends fetched.
     * data : {"current_page":1,"data":[{"id":60,"name":"User2","country":null,"user_image":[]},{"id":63,"name":"User5","country":null,"user_image":[]},{"id":98,"name":"Jack","country":"India","user_image":[{"id":30,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509428842-965B1263.jpg","is_cover_image":"0","created_at":"1509428842"},{"id":31,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509429819-464E6273.jpg","is_cover_image":"0","created_at":"1509429819"}]}],"from":1,"last_page":1,"next_page_url":null,"path":"http://tongy.ignivastaging.com/api/users/myConnection","per_page":15,"prev_page_url":null,"to":3,"total":3}
     */

    private int status;
    private String description;
    private ConnectionDataPojo data;

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

    public ConnectionDataPojo getData() {
        return data;
    }

    public void setData(ConnectionDataPojo data) {
        this.data = data;
    }

    public static class ConnectionDataPojo {
        /**
         * current_page : 1
         * data : [{"id":60,"name":"User2","country":null,"user_image":[]},{"id":63,"name":"User5","country":null,"user_image":[]},{"id":98,"name":"Jack","country":"India","user_image":[{"id":30,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509428842-965B1263.jpg","is_cover_image":"0","created_at":"1509428842"},{"id":31,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509429819-464E6273.jpg","is_cover_image":"0","created_at":"1509429819"}]}]
         * from : 1
         * last_page : 1
         * next_page_url : null
         * path : http://tongy.ignivastaging.com/api/users/myConnection
         * per_page : 15
         * prev_page_url : null
         * to : 3
         * total : 3
         */

        private int current_page;
        private int from;
        private int last_page;
        private Object next_page_url;
        private String path;
        private int per_page;
        private Object prev_page_url;
        private int to;
        private int total;
        private List<ContactDataPojo> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public Object getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(Object next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public Object getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(Object prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ContactDataPojo> getData() {
            return data;
        }

        public void setData(List<ContactDataPojo> data) {
            this.data = data;
        }

        public static class ContactDataPojo {
            /**
             * id : 60
             * name : User2
             * country : null
             * user_image : []
             */
             private int id;
            private String name;
            private String country;
            private List<ProfileResponsePojo.UserImageData> user_image;

            public UserCountryPojo getUser_country() {
                return user_country;
            }

            public void setUser_country(UserCountryPojo user_country) {
                this.user_country = user_country;
            }

            UserCountryPojo user_country;
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

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public List<ProfileResponsePojo.UserImageData> getUser_image() {
                return user_image;
            }

            public void setUser_image(List<ProfileResponsePojo.UserImageData> user_image) {
                this.user_image = user_image;
            }
        }
         public static class UserCountryPojo {

       /*     "user_image":[{"id":30,"user_id":"98",
                    "image":"http:\/\/tongy.ignivastaging.com\/images\/profile_image\/1509428842-965B1263.jpg",
                    "is_cover_image":"0","created_at":"1509428842"}*/

            int id;
            String country;

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getCountry_flag() {
                return country_flag;
            }

            public void setCountry_flag(String country_flag) {
                this.country_flag = country_flag;
            }

            String country_flag;

            public int getId() {
                return id;
            }



        }
    }
}
