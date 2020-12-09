package operator;

import redis.clients.jedis.Jedis;

public class num_operator {  //实现对num类型的操作
    private CounterSpec counterSpec;
    private Jedis jedis;

    public void setData(CounterSpec counterSpec,Jedis jedis) {
        this.counterSpec = counterSpec;
        this.jedis = jedis;
    }

    public String operator(){
        String result = " ";
        String key = counterSpec.getKeyFields();
        String value = counterSpec.getValueFields();
        String maxSize = counterSpec.getMaxSize();
        int values = Integer.parseInt(value);
        int max_Size = Integer.parseInt(maxSize);

        if(jedis.exists(key)){
            int key_value = Integer.parseInt(jedis.get(key));
            if(values != 0){
                if(counterSpec.getCounterName().equalsIgnoreCase("incr_num")){
                    if(key_value+values<=max_Size){
                        jedis.incrBy(key,values);
                        result = key + "+" + values + ",当前为" + jedis.get(key);
                    }
                    else{
                        jedis.set(key,maxSize);
                        result = key + "+" + values + "后超出上限" + ",当前为" + maxSize;
                    }
                }
                else{
                    if(key_value+values>=0){
                        jedis.incrBy(key,values);
                        result = key + values + ",当前为" + jedis.get(key);
                    }
                    else{
                        jedis.set(key,"0");
                        result = key + values + "后小于0" + ",当前为0";
                    }
                }
            }
            else{
                result = key + "的数值为" + jedis.get(key);
            }
        }
        else{
            jedis.set(key,"0");
            result = "增加" + key + ",当前数值为0";
            if(values != 0){
                if(counterSpec.getCounterName().equalsIgnoreCase("incr_num")){
                    if(0+values<=max_Size){
                        jedis.incrBy(key,values);
                        result += key + "+" + values + ",当前为" + jedis.get(key);
                    }
                    else{
                        jedis.set(key,maxSize);
                        result += key + "+" + values + "后超出上限" + ",当前为" + maxSize;
                    }
                }
                else{
                    if(0+values>=0){
                        jedis.incrBy(key,values);
                        result = key + values + ",当前为" + jedis.get(key);
                    }
                    else{
                        jedis.set(key,"0");
                        result = key + values + "后小于0" + ",当前为0";
                    }
                }
            }
            else{
                result = key + "的数值为" + jedis.get(key);
            }
        }
        return result;
    }
}
