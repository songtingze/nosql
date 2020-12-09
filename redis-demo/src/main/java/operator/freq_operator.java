package operator;

import redis.clients.jedis.Jedis;

public class freq_operator {  //实现对hash类型的操作
    private CounterSpec counterSpec;
    private Jedis jedis;
    private String freq_time;
    private String[] beginTime;
    private String[] endTime;

    public void setData(CounterSpec counterSpec,Jedis jedis,String freq_time) {
        this.counterSpec = counterSpec;
        this.jedis = jedis;
        this.freq_time = freq_time;
        String[] time = freq_time.split("\\-");
        beginTime = time[0].split("\\.");
        endTime = time[1].split("\\.");
        if(!jedis.hexists(counterSpec.getKeyFields(),"user")){
            jedis.hset(counterSpec.getKeyFields(),"user","0");
        }
    }

    public String getType(){
        String type = "";
        if(beginTime[0].equalsIgnoreCase(endTime[0])){
            if(beginTime[1].equalsIgnoreCase(endTime[1])){
                if(beginTime[2].equalsIgnoreCase(endTime[2])){
                    if(beginTime[3].equalsIgnoreCase(endTime[3])){
                        if(beginTime[4].equalsIgnoreCase(endTime[4])){
                            type = "null";
                        }
                        else type = "minute";
                    }
                    else type = "hour";
                }
                else type = "day";
            }
            else type = "month";
        }
        else type = "year";
        return type;
    }
    public String detail_operator(String str){
        String result = "";
        int value = Integer.parseInt(jedis.hget(counterSpec.getKeyFields(),"user"));
        int change = Integer.parseInt(counterSpec.getValueFields());

        if(jedis.hexists(counterSpec.getKeyFields(),str)){
            if(counterSpec.getCounterName().equalsIgnoreCase("incrFreq")){
                jedis.hincrBy(counterSpec.getKeyFields(),str,Integer.parseInt(counterSpec.getValueFields()));
                jedis.hincrBy(counterSpec.getKeyFields(),"user",Integer.parseInt(counterSpec.getValueFields()));
                result = counterSpec.getKeyFields() + "+" + counterSpec.getValueFields() + ",当前值为" + jedis.hget(counterSpec.getKeyFields(),"user");
            }
            else{
                if(value + change >= 0){
                    jedis.hincrBy(counterSpec.getKeyFields(),str,Integer.parseInt(counterSpec.getValueFields()));
                    jedis.hincrBy(counterSpec.getKeyFields(),"user",Integer.parseInt(counterSpec.getValueFields()));
                    result = counterSpec.getKeyFields() + counterSpec.getValueFields()+ ",当前值为" + jedis.hget(counterSpec.getKeyFields(),"user");
                }
                else{
                    result = counterSpec.getKeyFields() + counterSpec.getValueFields()+ ",当前值为0";
                }
            }
        }
        else{
            jedis.hset(counterSpec.getKeyFields(),str,counterSpec.getValueFields());
            if(counterSpec.getCounterName().equalsIgnoreCase("incrFreq")){
                jedis.hincrBy(counterSpec.getKeyFields(),str,Integer.parseInt(counterSpec.getValueFields()));
                jedis.hincrBy(counterSpec.getKeyFields(),"user",Integer.parseInt(counterSpec.getValueFields()));
                result = counterSpec.getKeyFields() + "+" + counterSpec.getValueFields() + ",当前值为" + jedis.hget(counterSpec.getKeyFields(),"user");
            }
            else{
                if(value + change >= 0){
                    jedis.hincrBy(counterSpec.getKeyFields(),str,Integer.parseInt(counterSpec.getValueFields()));
                    jedis.hincrBy(counterSpec.getKeyFields(),"user",Integer.parseInt(counterSpec.getValueFields()));
                    result = counterSpec.getKeyFields() + counterSpec.getValueFields()+ ",当前值为" + jedis.hget(counterSpec.getKeyFields(),"user");
                }
                else{
                    result = counterSpec.getKeyFields() + counterSpec.getValueFields()+ ",当前值为0";
                }
            }
        }
        return result;
    }
    public String type_operator(String type){
        String result = "";
        String[] time = counterSpec.getFields().split("\\.");
        switch (type){
            case "year":{
                String str = time[0];
                result = detail_operator(str);
                break;
            }
            case "month":{
                String str = time[0] + time[1];
                result = detail_operator(str);
                break;
            }
            case "day":{
                String str = time[0] + time[1] + time[2];
                result = detail_operator(str);
                break;
            }
            case "hour":{
                String str = time[0] + time[1] + time[2] + time[3];
                result = detail_operator(str);
                break;
            }
            case "minter":{
                String str = time[0] + time[1] + time[2] + time[3] + time[4];
                result = detail_operator(str);
                break;
            }
            case "null":{
                String str = time[0] + time[1] + time[2] + time[3] + time[4];
                result = detail_operator(str);
                break;
            }
        }
        return result;
    }
    public String show_detail(int begin,int end,int num,int prefix){
        String result = "";
        int change = 0;
        for(int i = begin;i <= end;i ++){
            if(prefix == 0){
                if(i>=Integer.parseInt(beginTime[num]) && i<=Integer.parseInt(endTime[num]) && jedis.hexists(counterSpec.getKeyFields(),i+"")){
                    int  value = Integer.parseInt(jedis.hget(counterSpec.getKeyFields(),i+""));
                    change += value;
                    result = freq_time + "期间," + counterSpec.getKeyFields() + "变化了" +change;
                }
            }
            else{
                if(i>=Integer.parseInt(beginTime[num]) && i<=Integer.parseInt(endTime[num]) && jedis.hexists(counterSpec.getKeyFields(),prefix+""+i)){
                    int  value = Integer.parseInt(jedis.hget(counterSpec.getKeyFields(),prefix+""+i));
                    change += value;
                    result = freq_time + "期间," + counterSpec.getKeyFields() + "变化了" +change;
                }
            }
        }
        return result;
    }
    public String show_freq(String type){
        String result = "";
        String[] time = counterSpec.getFields().split("\\.");
        switch (type){
            case "year":{
                result = show_detail(2000,2020,0,0);
                break;
            }
            case "month":{
                result = show_detail(1,12,1,Integer.parseInt(time[0]));
                break;
            }
            case "day":{
                result = show_detail(1,31,2,Integer.parseInt(time[0]+time[1]));
                break;
            }
            case "hour":{
                result = show_detail(0,23,3,Integer.parseInt(time[0]+time[1]+time[2]));
                break;
            }
            case "minute":{
                result = show_detail(0,59,4,Integer.parseInt(time[0]+time[1]+time[2]+time[3]));
                break;
            }
            case "null":{
                result = "时间段不存在";
                break;
            }
        }
        return result;
    }
    public String operator(){
        String result = " ";
        if(!counterSpec.getValueFields().equalsIgnoreCase("0")){
            result = type_operator(getType());
        }
        else {
            result = show_freq(getType());
        }
        return result;
    }
}
