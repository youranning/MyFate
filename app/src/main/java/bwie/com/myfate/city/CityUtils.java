package bwie.com.myfate.city;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.OptionsPickerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import bwie.com.myfate.R;
import bwie.com.myfate.city.bean.AllAreaBean;
import bwie.com.myfate.city.bean.ProvinceBean;

public class CityUtils {


    //城市列表选择
    OptionsPickerView optionsPickerView;

    ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();


    private static class CityHelper {
        private static CityUtils INSTANCE = new CityUtils();
    }


    public static CityUtils getInstance() {
        return CityHelper.INSTANCE;
    }

    //获取全国所有城市列表
    public void getAllCity(Context context, final CityNameInterface cityNameInterface) {

        optionsPickerView = new OptionsPickerView(context);

        AllAreaBean allAreaBean = null;

        try {
            InputStream inputStream = context.getAssets().open("AllCity.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String city = new String(buffer);

            allAreaBean = JSON.parseObject(city, AllAreaBean.class);


            //获取所有的省份名字
            for (int i = 0; i < allAreaBean.getProvince().size(); i++) {
                ProvinceBean provinceBean = new ProvinceBean();
                provinceBean.setId(i);
                provinceBean.setName(allAreaBean.getProvince().get(i).getName());
                options1Items.add(provinceBean);

                //存放地区
                ArrayList<ArrayList<String>> listArea = new ArrayList<>();
                //存放城市名字
                ArrayList<String> listCity = new ArrayList<>();
                for (int j = 0; j < allAreaBean.getProvince().get(i).getCity().size(); j++) {
                    listCity.add(allAreaBean.getProvince().get(i).getCity().get(j).getName());

                    listArea.add((ArrayList<String>) allAreaBean.getProvince().get(i).getCity().get(j).getArea());
                }
                options3Items.add(listArea);
                options2Items.add(listCity);
            }

            //三级联动效果
            optionsPickerView.setPicker(options1Items, options2Items, options3Items, true);
            optionsPickerView.setTitle("城市");
            //设置是否有滚动效果
            optionsPickerView.setCyclic(false, false, false);
            optionsPickerView.show();
            optionsPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    //返回的分别是三个级别的选中位置  判断省 和 城市 是否重名
                    String area = "";
                    if (options1Items.get(options1).getName().equals(options2Items.get(options1).get(option2))) {
                        area = options1Items.get(options1).getName() + " "
                                + options3Items.get(options1).get(option2).get(options3) + "  ";
                    } else {
                        area = options1Items.get(options1).getName() + " "
                                + options2Items.get(options1).get(option2) + " "
                                + options3Items.get(options1).get(option2).get(options3) + "  ";
                    }
                    //获取所有的城市名称
                    cityNameInterface.getCityName(area);
                }
            });

//            LogUtils.i(city);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}