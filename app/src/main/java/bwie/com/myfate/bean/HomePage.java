package bwie.com.myfate.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.List;

/**
 * Created by $USER_NAME on 2017/7/13.
 */
public class HomePage {

    /**
     * result_message : success
     * data : {"area":"安徽省  安庆市   枞阳县","lasttime":1499951629553,"createtime":1499828541161,"gender":"男","introduce":"jm","imagePath":"http://qhb.2dyt.com/MyInterface/images/5bde0a3a-aae1-477a-8c95-ad744bd0ced1.jpg","nickname":"我是我","userId":3,"relation":0,"photolist":[{"picWidth":720,"timer":1499844751582,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/8e8cadaf-503b-4a19-b0b5-2936585a05f4.jpg","albumId":10,"userId":3},{"picWidth":720,"timer":1499844756732,"picHeight":960,"imagePath":"http://qhb.2dyt.com/MyInterface/images/d9ed1f2d-d3cc-4b85-9c94-2d37916d8ee2.jpg","albumId":11,"userId":3},{"picWidth":720,"timer":1499861809664,"picHeight":960,"imagePath":"http://qhb.2dyt.com/MyInterface/images/dc31ea34-469a-444c-b988-229354a683dc.jpg","albumId":25,"userId":3},{"picWidth":720,"timer":1499861860123,"picHeight":883,"imagePath":"http://qhb.2dyt.com/MyInterface/images/04332eaa-149b-4b63-9a6d-00f664c1c188.jpg","albumId":26,"userId":3},{"picWidth":720,"timer":1499861864331,"picHeight":540,"imagePath":"http://qhb.2dyt.com/MyInterface/images/6a2b3bf7-98a0-4ca4-85e5-3f117ab7947c.jpg","albumId":27,"userId":3},{"picWidth":720,"timer":1499905257791,"picHeight":960,"imagePath":"http://qhb.2dyt.com/MyInterface/images/19d61324-2ee2-4d41-8842-d157c95e539a.jpg","albumId":58,"userId":3},{"picWidth":720,"timer":1499905261368,"picHeight":1280,"imagePath":"http://qhb.2dyt.com/MyInterface/images/ba5a2de4-96e3-4717-8e1e-650ac04fd212.jpg","albumId":59,"userId":3},{"picWidth":720,"timer":1499950656398,"picHeight":540,"imagePath":"http://qhb.2dyt.com/MyInterface/images/047f8419-389a-454e-82e7-dbf03efcd8c9.jpg","albumId":118,"userId":3},{"picWidth":720,"timer":1499950661098,"picHeight":960,"imagePath":"http://qhb.2dyt.com/MyInterface/images/82488315-dda0-49d5-9c5f-feadb3fd3b1c.jpg","albumId":119,"userId":3},{"picWidth":720,"timer":1499950671589,"picHeight":1280,"imagePath":"http://qhb.2dyt.com/MyInterface/images/14187c50-d663-479e-8ef9-c7e1c0123245.jpg","albumId":120,"userId":3}]}
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



}
