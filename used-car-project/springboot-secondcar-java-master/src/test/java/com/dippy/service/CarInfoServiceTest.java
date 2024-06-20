package com.dippy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dippy.entity.CarInfo;
import com.dippy.mapper.CarInfoMapper;
import com.dippy.vo.CarBrandVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class CarInfoServiceTest {

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private CarInfoMapper carInfoMapper;

    @Test
    public void testFindAllScreen() {
        // System.out.println(carInfoService.list(new QueryWrapper<CarInfo>().select("distinct(car_brand)")));
        List<Object> brands = carInfoService.listObjs(new QueryWrapper<CarInfo>().select("distinct(car_brand)"));
        System.out.println(brands);

        List<CarBrandVo> allCarBrand = carInfoService.getAllCarBrand();
        allCarBrand.add(0, new CarBrandVo("全部", true));
        System.out.println(allCarBrand);

        System.out.println(carInfoService.listObjs(new QueryWrapper<CarInfo>().select("distinct(car_type)")));

        System.out.println(carInfoService.listObjs(new QueryWrapper<CarInfo>().select("distinct(car_series)")));

        System.out.println(carInfoService.listMaps(new QueryWrapper<CarInfo>().select("distinct(car_brand)")));

    }

    @Test
    public void testAllCarInfo() {
        System.out.println(carInfoService.getAllCarInfo().toString());
    }


    @Test
    public void testUT() {
        String data = "carBrand=大众&carSeries=5系&carType=轿车&carFuelType=机油&carColor=灰色";
        StringBuffer strbuf = new StringBuffer();
        StringBuffer strbuf2 = new StringBuffer();
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < data.length(); i++) {

            if (data.substring(i, i + 1).equals("=")) {

                for (int n = i + 1; n < data.length(); n++) {
                    if (data.substring(n, n + 1).equals("&") || n == data.length() - 1) {
                        map.put(strbuf.toString(), strbuf2);
                        strbuf = new StringBuffer("");
                        strbuf2 = new StringBuffer("");
                        i = n;
                        break;
                    }
                    strbuf2.append(data.substring(n, n + 1));
                }
                continue;
            }
            strbuf.append(data.substring(i, i + 1));
        }
        for (String key : map.keySet()) {
            System.out.println(key + "=" + map.get(key));
        }
    }


    @Test
    public void test3() {
        String strUrlParam = "carBrand=大众&carSeries=5系&carType=轿车&carFuelType=机油&carColor=灰色";

        Map<String, String> mapRequest = new HashMap();

        String[] arrSplit = null;

        arrSplit = strUrlParam.split("[&]");

        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");

                }
            }
        }

        for (String key : mapRequest.keySet()) {
            System.out.println(key + "=" + mapRequest.get(key));
        }
    }

    /**
     * 批量修改文件名
     */
    @Test
    public void test4() {
      /*  String path = "C:/大数据/第1章：hadoop";
        File file = new File(path);
        String[] flist = file.list();
        Arrays.asList(flist).stream().forEach(x -> {
            if(x.contains("大数据－0")){
                String cc = x.replace("大数据－0", "大数据-");
                File old   = new File(path + File.separatorChar + x);
                File nfile = new File(path + File.separatorChar + cc);
                old.renameTo(nfile);
            }
        });
        // Arrays.asList(flist).stream().forEach(x ->System.out.println(x));
    }*/


    }
}
