package bwie.com.myfate.bean;

import java.util.List;

/**
 * Created by $USER_NAME on 2017/7/15.
 */

public class Friend {

    /**
     * result_message : success
     * data : [{"area":"河北省-邯郸市-磁县","picWidth":720,"createtime":1500078886710,"picHeight":960,"gender":"男","lng":116.299632,"introduce":"1","imagePath":"http://qhb.2dyt.com/MyInterface/images/e9779dc2-90ad-4012-b583-a904debbab00.jpg","userId":11,"relationtime":1500080504465,"yxpassword":"7kfDlDa0","relation":0,"password":"c4ca4238a0b923820dcc509a6f75849b","lasttime":1500080279484,"phone":"18303333102","nickname":"皓子","age":"20","lat":40.040416},{"area":"广东省-潮州市-潮安县","picWidth":720,"createtime":1500079922765,"picHeight":960,"gender":"男","lng":116.294962,"introduce":"111","imagePath":"http://qhb.2dyt.com/MyInterface/images/524d630a-b957-4a0a-823c-070525e44d6d.jpg","userId":25,"relationtime":1500080275567,"yxpassword":"Hev09v28","relation":0,"password":"698d51a19d8a121ce581499d7b701668","lasttime":1500079922765,"phone":"13965485514","nickname":"777","age":"20","lat":40.039205},{"area":"吉林省-白城市-大安市","picWidth":150,"createtime":1500078523783,"picHeight":200,"gender":"男","lng":116.300617,"introduce":"醉把佳人成双对。","imagePath":"http://qhb.2dyt.com/MyInterface/images/8c2aa318-0f4d-4a93-aed8-12a7fbc798a4.jpg","userId":5,"relationtime":1500079764706,"yxpassword":"806FRU05","relation":0,"password":"80ac32fccaa9f0030bea18b8463d66b2","lasttime":1500080159489,"phone":"18310496520","nickname":"一人我饮酒醉。","age":"39","lat":40.04027},{"area":"安徽省-安庆市-枞阳县","picWidth":720,"createtime":1500078095521,"picHeight":720,"gender":"男","lng":116.293931,"introduce":"hhaha","imagePath":"http://qhb.2dyt.com/MyInterface/images/925828cb-dd0f-435f-af9b-8070e82f09c4.jpg","userId":1,"relationtime":1500079762079,"yxpassword":"3GP13068","relation":0,"password":"b59c67bf196a4758191e42f76670ceba","lasttime":1500079223745,"phone":"13645616851","nickname":"安徽","age":"18","lat":40.039161},{"area":"河南省 焦作市 孟州市","picWidth":720,"createtime":1500079068431,"picHeight":720,"gender":"男","lng":116.293973,"introduce":"世间万物皆加于吾身之诸妄。","imagePath":"http://qhb.2dyt.com/MyInterface/images/002cf903-a86d-40b9-93c6-8a1296730efc.jpg","userId":15,"relationtime":1500079752687,"yxpassword":"kYV1602z","relation":0,"password":"81dc9bdb52d04dc20036dbd8313ed055","lasttime":1500080136327,"phone":"13353910788","nickname":"Reinhardt","age":"20","lat":40.039316}]
     * result_code : 200
     */

    private String result_message;
    private int result_code;
    private List<DataBean> data;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * area : 河北省-邯郸市-磁县
         * picWidth : 720
         * createtime : 1500078886710
         * picHeight : 960
         * gender : 男
         * lng : 116.299632
         * introduce : 1
         * imagePath : http://qhb.2dyt.com/MyInterface/images/e9779dc2-90ad-4012-b583-a904debbab00.jpg
         * userId : 11
         * relationtime : 1500080504465
         * yxpassword : 7kfDlDa0
         * relation : 0
         * password : c4ca4238a0b923820dcc509a6f75849b
         * lasttime : 1500080279484
         * phone : 18303333102
         * nickname : 皓子
         * age : 20
         * lat : 40.040416
         */

        private String area;
        private int picWidth;
        private long createtime;
        private int picHeight;
        private String gender;
        private double lng;
        private String introduce;
        private String imagePath;
        private int userId;
        private long relationtime;
        private String yxpassword;
        private int relation;
        private String password;
        private long lasttime;
        private String phone;
        private String nickname;
        private String age;
        private double lat;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getPicWidth() {
            return picWidth;
        }

        public void setPicWidth(int picWidth) {
            this.picWidth = picWidth;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public int getPicHeight() {
            return picHeight;
        }

        public void setPicHeight(int picHeight) {
            this.picHeight = picHeight;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public long getRelationtime() {
            return relationtime;
        }

        public void setRelationtime(long relationtime) {
            this.relationtime = relationtime;
        }

        public String getYxpassword() {
            return yxpassword;
        }

        public void setYxpassword(String yxpassword) {
            this.yxpassword = yxpassword;
        }

        public int getRelation() {
            return relation;
        }

        public void setRelation(int relation) {
            this.relation = relation;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }
}
