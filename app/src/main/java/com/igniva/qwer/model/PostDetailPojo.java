package com.igniva.qwer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igniva-android13 on 13/11/17.
 */

public class PostDetailPojo implements Serializable {

    /**
     * status : 200
     * description : Posts fetch successfully.
     * data : {"id":49,"user_id":"98","post_type":"meeting","title":"meeting section","description":"great going....keep it up...","image":"","price":"0","currency":"","start_date_time":"1508493600","end_date_time":"1514718000","class_type":"","class_location":"chandigarh","lat":"20.220000","lng":"20.000000","status":"1","created_at":"1510550094","post_user":{"id":98,"name":"jack","user_image":[{"id":30,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509428842-965B1263.jpg","is_cover_image":"0","created_at":"1509428842"},{"id":31,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509429819-464E6273.jpg","is_cover_image":"0","created_at":"1509429819"}]},"post_comment":[],"post_fav":[],"post_comment_count":[]}
     */

    private int status;
    private String description;
    public DataPojo data;

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

    public DataPojo getData() {
        return data;
    }

    public void setData(DataPojo data) {
        this.data = data;
    }

    public static class DataPojo implements Serializable {
        ArrayList<CommentPojo> post_comment;
        /**
         * id : 49
         * user_id : 98
         * post_type : meeting
         * title : meeting section
         * description : great going....keep it up...
         * image :
         * price : 0
         * currency :
         * start_date_time : 1508493600
         * end_date_time : 1514718000
         * class_type :
         * class_location : chandigarh
         * lat : 20.220000
         * lng : 20.000000
         * status : 1
         * created_at : 1510550094
         * post_user : {"id":98,"name":"jack","user_image":[{"id":30,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509428842-965B1263.jpg","is_cover_image":"0","created_at":"1509428842"},{"id":31,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509429819-464E6273.jpg","is_cover_image":"0","created_at":"1509429819"}]}
         * post_comment : []
         * post_fav : []
         * post_comment_count : []
         */

        private int id;
        private String user_id;
        private String post_type;
        private String title;
        private String description;
        private String image;
        private String price;
        private String currency;
        private String start_date_time;
        private String end_date_time;
        private String class_type;
        private String class_location;
        private String lat;
        private String lng;
        private String status;
        private String created_at;
        private PostUserPojo post_user;
        private List<PostUserPojo.FavPostPojo> post_fav;
        private ArrayList<PostCommmentCount> post_comment_count;

        public ArrayList<PostMember> getPost_member() {
            return post_member;
        }

        public void setPost_member(ArrayList<PostMember> post_member) {
            this.post_member = post_member;
        }

        public ArrayList<PostMember> post_member;

        public List<PostUserPojo.FavPostPojo> getPost_fav() {
            return post_fav;
        }

        public void setPost_fav(List<PostUserPojo.FavPostPojo> post_fav) {
            this.post_fav = post_fav;
        }

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

        public String getPost_type() {
            return post_type;
        }

        public void setPost_type(String post_type) {
            this.post_type = post_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getStart_date_time() {
            return start_date_time;
        }

        public void setStart_date_time(String start_date_time) {
            this.start_date_time = start_date_time;
        }

        public String getEnd_date_time() {
            return end_date_time;
        }

        public void setEnd_date_time(String end_date_time) {
            this.end_date_time = end_date_time;
        }

        public String getClass_type() {
            return class_type;
        }

        public void setClass_type(String class_type) {
            this.class_type = class_type;
        }

        public String getClass_location() {
            return class_location;
        }

        public void setClass_location(String class_location) {
            this.class_location = class_location;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public PostUserPojo getPost_user() {
            return post_user;
        }

        public void setPost_user(PostUserPojo post_user) {
            this.post_user = post_user;
        }

        public ArrayList<CommentPojo> getPost_comment() {
            return post_comment;
        }

        public void setPost_comment(ArrayList<CommentPojo> post_comment) {
            this.post_comment = post_comment;
        }

        public ArrayList<PostCommmentCount> getPost_comment_count() {
            return post_comment_count;
        }

        public void setPost_comment_count(ArrayList<PostCommmentCount> post_comment_count) {
            this.post_comment_count = post_comment_count;
        }

        public class PostCommmentCount implements Serializable {

            String post_id, count;

            public String getPost_id() {
                return post_id;
            }

            public void setPost_id(String post_id) {
                this.post_id = post_id;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }
        }

        public class PostUserPojo implements Serializable {
            /**
             * id : 98
             * name : jack
             * user_image : [{"id":30,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509428842-965B1263.jpg","is_cover_image":"0","created_at":"1509428842"},{"id":31,"user_id":"98","image":"http://tongy.ignivastaging.com/images/profile_image/1509429819-464E6273.jpg","is_cover_image":"0","created_at":"1509429819"}]
             */

            private int id;
            private String name;
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

            public List<UserImagePojo> getUser_image() {
                return user_image;
            }

            public void setUser_image(List<UserImagePojo> user_image) {
                this.user_image = user_image;
            }

            public class FavPostPojo implements Serializable {
                String user_id;
                String post_id;

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public String getPost_id() {
                    return post_id;
                }

                public void setPost_id(String post_id) {
                    this.post_id = post_id;
                }
            }

            public class UserImagePojo implements Serializable {
                /**
                 * id : 30
                 * user_id : 98
                 * image : http://tongy.ignivastaging.com/images/profile_image/1509428842-965B1263.jpg
                 * is_cover_image : 0
                 * created_at : 1509428842
                 */

                private int id;
                private String user_id;
                private String image;
                private String is_cover_image;
                private String created_at;

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
            }
        }
    }

    public static class PostMember implements Serializable {
       /*  [{
            "id": 12,
                    "post_id": "114",
                    "presenter": "[tanmey",
                    "created_at": "1510728595"
        }, {
            "id": 13,
                    "post_id": "114",
                    "presenter": " prerna]",
                    "created_at": "1510728595"
        }],*/

        public String getPresenter() {
            return presenter;
        }

        public void setPresenter(String presenter) {
            this.presenter = presenter;
        }

        public String getPost_id() {
            return post_id;
        }

        public void setPost_id(String post_id) {
            this.post_id = post_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String presenter,post_id,created_at;
       public int id;
    }
}
