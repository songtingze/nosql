package operator;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class set_operator {  //实现对set类型的操作
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

        jedis.sadd(key,value);
        result = key + "添加" + value;
        return result;
    }
    public String show(){
        String result = "";
        String key = counterSpec.getKeyFields();

        Set<String> smembers = jedis.smembers(key);
        result = key + "的值为:" + smembers.toString();

        return result;

    }
}
