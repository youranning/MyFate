package bwie.com.myfate.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

public  class PhotolistBean {
        /**
         * picWidth : 720
         * timer : 1499844751582
         * picHeight : 720
         * imagePath : http://qhb.2dyt.com/MyInterface/images/8e8cadaf-503b-4a19-b0b5-2936585a05f4.jpg
         * albumId : 10
         * userId : 3
         */

        private int picWidth;
        private long timer;
        private int picHeight;
        private String imagePath;
        private int albumId;
        private int userId;


        public int getPicWidth() {
            return picWidth;
        }

        public void setPicWidth(int picWidth) {
            this.picWidth = picWidth;
        }

        public long getTimer() {
            return timer;
        }

        public void setTimer(long timer) {
            this.timer = timer;
        }

        public int getPicHeight() {
            return picHeight;
        }

        public void setPicHeight(int picHeight) {
            this.picHeight = picHeight;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public int getAlbumId() {
            return albumId;
        }

        public void setAlbumId(int albumId) {
            this.albumId = albumId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }


    }
