package operator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


public class readJson {   //读取json文件
    // counter映射
    private static final HashMap<String, CounterSpec> counterMap = new HashMap<>();
    // action映射
    private static final HashMap<String, ActionSpec> actionMap = new HashMap<>();

    public HashMap<String, CounterSpec> readCounter() throws IOException, JSONException {
        // 清空
        counterMap.clear();

        // 获取counters的json配置文件

        String countersPath = "src/main/resources/counters.json";

        //获取配置文件，并转为String
        InputStream inputStream = new FileInputStream(countersPath);
        String countersText = IOUtils.toString(inputStream, StandardCharsets.UTF_8);


        // 将配置文件中的counter转为实体类并放入counterMap中
        JSONObject obj = JSON.parseObject(countersText);
        JSONArray counters = obj.getJSONArray("counters");
        for(int i = 0; i < counters.size(); i++) {
            CounterSpec counterSpec = counters.getJSONObject(i).toJavaObject(CounterSpec.class);
            counterMap.put(counterSpec.getCounterName(), counterSpec);
        }

        return counterMap;
    }
    public HashMap<String, ActionSpec> readAction() throws IOException, JSONException {
        // 清空

        actionMap.clear();
        // 获取actions的json配置文件
        String actionsPath = "src/main/resources/actions.json";

        //获取配置文件，并转为String
        InputStream inputStream = new FileInputStream(actionsPath);
        String actionsText = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        // 将配置文件中的action转为实体类并放入actionMap中
        JSONObject obj1 = JSON.parseObject(actionsText);
        JSONArray actions = obj1.getJSONArray("actions");
        for(int i = 0; i < actions.size(); i++) {
            ActionSpec actionSpec = actions.getJSONObject(i).toJavaObject(ActionSpec.class);
            actionMap.put(actionSpec.getName(),actionSpec);
        }

        return actionMap;
    }
}
