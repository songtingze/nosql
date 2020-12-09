package operator;

import redis.clients.jedis.Jedis;

import java.util.List;

public class list_operator {    //实现对list类型的操作
    private CounterSpec counterSpec;
    private Jedis jedis;

    public void setData(CounterSpec counterSpec,Jedis jedis) {
        this.counterSpec = counterSpec;
        this.jedis = jedis;
    }
    public String add(){
        String result = "";
        String key = counterSpec.getKeyFields();
        String value = counterSpec.getValueFields();

        jedis.rpush(key,value);
        result = key + "添加" + value;
        return result;
    }
    public String show(){
        String result = "";
        String key = counterSpec.getKeyFields();
        if(jedis.exists(key)){
            List<String> mylist = jedis.lrange(key, 0, -1);
            result = key + "值为:";
            for(int i = 0 ;i < mylist.size();i++){
                result += mylist.get(i) + " ";
            }
        }
        else{
            result =  key + "不存在";
        }
        return result;
    }
}
