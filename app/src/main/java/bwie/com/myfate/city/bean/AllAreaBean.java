package bwie.com.myfate.city.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by $USER_NAME on 2017/7/6.
 */

public class AllAreaBean implements Serializable{

    private List<ProvinceListBean> province;

    public List<ProvinceListBean> getProvince() {
        return province;
    }

    public void setProvince(List<ProvinceListBean> province) {
        this.province = province;
    }
}
