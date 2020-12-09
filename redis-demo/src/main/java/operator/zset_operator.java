package operator;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class zset_operator {  //实现对zset类型的操作
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
        int score = 10;

        jedis.zadd(key,score,value);
        result = key + "添加" + value;
        return result;
    }
    public String show(){
        String result = "";
        String key = counterSpec.getKeyFields();

        Set<String> zrange = jedis.zrange(key, 0, -1);
        result = key + "的值为:" + zrange.toString();

        return result;

    }
}
