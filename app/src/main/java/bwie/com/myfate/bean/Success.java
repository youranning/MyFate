package bwie.com.myfate.bean;

/**
 * Created by $USER_NAME on 2017/7/10.
 */

public class Success {

    /**
     * result_message : success
     * data : {"area":"北京 东城区","password":"202cb962ac59075b964b07152d234b70","lasttime":1499691649657,"createtime":1499691168685,"gender":"男","lng":0,"phone":"15088888888","introduce":"123","imagePath":"http://dyt-pict.oss-cn-beijing.aliyuncs.com/dliao/default_man.jpg","nickname":"123","userId":191,"lat":0}
     * result_code : 200
     */

    private String result_message;
    private DataBean data;
    private int result_code;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public static class DataBean {
        /**
         * area : 北京 东城区
         * password : 202cb962ac59075b964b07152d234b70
         * lasttime : 1499691649657
         * createtime : 1499691168685
         * gender : 男
         * lng : 0
         * phone : 15088888888
         * introduce : 123
         * imagePath : http://dyt-pict.oss-cn-beijing.aliyuncs.com/dliao/default_man.jpg
         * nickname : 123
         * userId : 191
         * lat : 0
         */

        private String area;
        private String password;
        private long lasttime;
        private long createtime;
        private String gender;
        private String lng;
        private String phone;
        private String introduce;
        private String imagePath;
        private String nickname;
        private int userId;
        private String lat;
        String yxpassword;

        public String getYxpassword() {
            return yxpassword;
        }

        public void setYxpassword(String yxpassword) {
            this.yxpassword = yxpassword;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public long getLasttime() {
            return lasttime;
        }

        public void setLasttime(long lasttime) {
            this.lasttime = lasttime;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }



        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
}
