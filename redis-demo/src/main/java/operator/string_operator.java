package operator;

import redis.clients.jedis.Jedis;

public class string_operator {  //实现对string类型的操作
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

        if(jedis.exists(key)){
            if(jedis.get(key) == null){
                jedis.set(key,value);
                result = key + "添加值" + value;
            }
            else{
                jedis.append(key,value);
                result = key + "添加值" + value;
            }
        }
        else{
            jedis.set(key,value);
            result = "新增" + key + "并添加值" + value;
        }
        return result;
    }
    public String delete(){
        String result = "";
        String key = counterSpec.getKeyFields();

        if(jedis.exists(key)){
            if(jedis.get(key) != null){
                jedis.set(key,"");
                result = key + "删除值";
            }
            else{
                result = key + "值为空";
            }
        }
        else{
            result = "不存在" + key;
        }
        return result;
    }
    public String show(){
        String result = "";
        String key = counterSpec.getKeyFields();
        if(jedis.exists(key)){
            if(jedis.get(key) != null){
                result = key + "的值为:" + jedis.get(key);
            }
            else{
                result = key + "的值为空";
            }
        }
        else{
            jedis.set(key,"");
            result = "新增" + key + "且值为空";
        }
        return result;
    }
}
