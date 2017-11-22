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
            private List<UserImagePojo> user_image;

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

            public List<UserImagePojo> getUser_image() {
                return user_image;
            }

            public void setUser_image(List<UserImagePojo> user_image) {
                this.user_image = user_image;
            }
        }
        public static class UserImagePojo {

       /*     "user_image":[{"id":30,"user_id":"98",
                    "image":"http:\/\/tongy.ignivastaging.com\/images\/profile_image\/1509428842-965B1263.jpg",
                    "is_cover_image":"0","created_at":"1509428842"}*/

       String id;
            String user_id;
            String image;

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

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            String is_cover_image;
            String created_at;

        }
    }
}
