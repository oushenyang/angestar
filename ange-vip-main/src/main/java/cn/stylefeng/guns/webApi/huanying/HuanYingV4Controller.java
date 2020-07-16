package cn.stylefeng.guns.webApi.huanying;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/7/16
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/api/v4")
public class HuanYingV4Controller {

    @RequestMapping("/apptoken")
    @ResponseBody
    public String apptoken(){
        Map map = new HashMap<String, String>();
        map.put("data","r52fVRuqRKZwpOlKry70HcCa3ExP+fVYgu4LaHDYX1/YJbZYQroqqwghonFtEyWoucpKEFLotFlnfQUrLFJTEALe3YtzEmWc+BK+GsV8Mw20kuP/QxstoeLcMNV2SHSPLkxpwrsRqWpPTIHgRmII7TEMU2mGvNI3cOnuoWayZ1UTPZDIqdwarePFTBvTcNFhtoIhi4vkhO6+1/rZS2Y75ws/7SZrFYdhQT0Ba43IKenGc2MNKjjELHK22kYM6iLkZhDHL+tD+RYUOLnFIXC+Z6oeHlnXIzg3LbAupdbukSyTFGQdN2REvCG/UsF3kIYGJuIloaDN7n+AEv5ybz9B7TsV9JrG1trbrC78DRoK3+1XFIHUa9s91/eg0G14bIQU99/HMC/SldNTzVMOtczGe8MWIRtmUMQXER3WiQo+/m1NHORcuF3v0K5DzgWLQeb698gNoFz808GklLu9Lrur3M6gxt5N6oIYyWWw4N96+kIh8PGaj+M42Thor2nyD1qN40O4W7/z7rMuWv60jh5OPETFwJcke7KSY1Wh7d0G7gat9GymxzuUa8/dfJdWHJ54zJ42OGfpqMXtX5JC35V2WgsNLHrUAIMhyGcEHJCdlCsYqOBn/8yrDeo3TTUfzQ3BZQB6laVNpaqb2X3b9HnFtav+iFBmTfrgzNa2xEyEJzuIWtHSkEFO50cBECer0Rsc");
        map.put("message", "ok");
        map.put("code", 200);
        JSONObject json = new JSONObject(map);
        return json.toString();
    }
}
