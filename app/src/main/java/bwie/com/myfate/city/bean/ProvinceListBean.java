package bwie.com.myfate.city.bean;

import java.io.Serializable;
import java.util.List;

public class ProvinceListBean implements Serializable {

    private String name;
    private List<CityListBean> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityListBean> getCity() {
        return city;
    }

    public void setCity(List<CityListBean> city) {
        this.city = city;
    }

}