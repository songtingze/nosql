package operator;

import com.bjtu.redis.JedisInstance;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class main {
    private static final readJson readjson = new readJson();
    private static HashMap<String, CounterSpec> counterMap;
    private static HashMap<String, ActionSpec> actionMap;
    private static num_operator num_operators = new num_operator();
    private static freq_operator freq_operators = new freq_operator();
    private static list_operator list_operators = new list_operator();
    private static string_operator string_operators = new string_operator();
    private static set_operator set_operators = new set_operator();
    private static zset_operator zset_operators = new zset_operator();
    private static Jedis jedis = JedisInstance.getInstance().getResource();
    private static MyFileListener myfilelistener = new MyFileListener();

    public static void setData() throws IOException {  //初始化数据
        System.out.println("读取Json配置文件中...");
        counterMap = readjson.readCounter();
        actionMap = readjson.readAction();
        System.out.println("读取完毕！");
    }
    public static String getActions(){   //获取所有action
        String actions = "actions:\n";
        actions += "<1> ";
        actions += actionMap.get("incr_num").getName() + "\n";
        actions += "<2> ";
        actions += actionMap.get("decr_num").getName() + "\n";
        actions += "<3> ";
        actions += actionMap.get("show_num").getName() + "\n";
        actions += "<4> ";
        actions += actionMap.get("incrFreq").getName() + "\n";
        actions += "<5> ";
        actions += actionMap.get("decrFreq").getName() + "\n";
        actions += "<6> ";
        actions += actionMap.get("showFreq").getName() + "\n";
        actions += "<7> ";
        actions += actionMap.get("showFreqIn").getName() + "\n";
        actions += "<8> ";
        actions += actionMap.get("showFreqOut").getName() + "\n";
        actions += "<9> ";
        actions += actionMap.get("showFreqInOut").getName() + "\n";
        actions += "<10> ";
        actions += actionMap.get("add_str").getName() + "\n";
        actions += "<11> ";
        actions += actionMap.get("delete_str").getName() + "\n";
        actions += "<12> ";
        actions += actionMap.get("show_str").getName() + "\n";
        actions += "<13> ";
        actions += actionMap.get("add_list").getName() + "\n";
        actions += "<14> ";
        actions += actionMap.get("show_list").getName() + "\n";
        actions += "<15> ";
        actions += actionMap.get("add_set").getName() + "\n";
        actions += "<16> ";
        actions += actionMap.get("show_set").getName() + "\n";
        actions += "<17> ";
        actions += actionMap.get("add_zSet").getName() + "\n";
        actions += "<18> ";
        actions += actionMap.get("show_zSet").getName() + "\n";

        return actions;
    }
    public static void action_operator(){  //对应action的操作
        System.out.print(getActions());
        System.out.println("<0> 返回:");
        System.out.print("选择action编号:");
        Scanner sc=new Scanner(System.in);
        String operator = sc.nextLine();
        int int_operator =Integer.parseInt(operator);

        if(int_operator<=actionMap.size() && int_operator>0){
            switch(int_operator){
                case 1:{
                    num_operators.setData(counterMap.get(actionMap.get("incr_num").getSave_counterName()),jedis);
                    System.out.println(num_operators.operator());
                    num_operators.setData(counterMap.get(actionMap.get("incr_num").getRetrieve_counterName()),jedis);
                    System.out.println(num_operators.operator() + "\n");
                    break;
                }
                case 2:{
                    num_operators.setData(counterMap.get(actionMap.get("decr_num").getSave_counterName()),jedis);
                    System.out.println(num_operators.operator());
                    num_operators.setData(counterMap.get(actionMap.get("decr_num").getRetrieve_counterName()),jedis);
                    System.out.println(num_operators.operator());
                    break;
                }
                case 3:{
                    num_operators.setData(counterMap.get(actionMap.get("show_num").getRetrieve_counterName()),jedis);
                    System.out.println(num_operators.operator());
                    break;
                }
                case 4:{
                    String freq_time = counterMap.get("showFreq").getFields();
                    freq_operators.setData(counterMap.get(actionMap.get("incrFreq").getSave_counterName()),jedis,freq_time);
                    System.out.println(freq_operators.operator());
                    freq_operators.setData(counterMap.get(actionMap.get("incrFreq").getRetrieve_counterName()),jedis,freq_time);
                    System.out.println(freq_operators.operator());
                    break;
                }
                case 5:{
                    String freq_time = counterMap.get("showFreq").getFields();
                    freq_operators.setData(counterMap.get(actionMap.get("decrFreq").getSave_counterName()),jedis,freq_time);
                    System.out.println(freq_operators.operator());
                    freq_operators.setData(counterMap.get(actionMap.get("decrFreq").getRetrieve_counterName()),jedis,freq_time);
                    System.out.println(freq_operators.operator());
                    break;
                }
                case 6:{
                    String freq_time = counterMap.get("showFreq").getFields();
                    freq_operators.setData(counterMap.get(actionMap.get("showFreq").getRetrieve_counterName()),jedis,freq_time);
                    System.out.println(freq_operators.operator());
                    break;
                }
                case 7:{
                    String freq_time = counterMap.get("showFreq").getFields();
                    freq_operators.setData1(counterMap.get(actionMap.get("showFreqIn").getSave_counterName()),jedis,freq_time);
                    System.out.println(freq_operators.showIn());
                    break;
                }
                case 8:{
                    String freq_time = counterMap.get("showFreq").getFields();
                    freq_operators.setData1(counterMap.get(actionMap.get("showFreqOut").getSave_counterName()),jedis,freq_time);
                    System.out.println(freq_operators.showOut());
                    break;
                }
                case 9:{
                    String freq_time = counterMap.get("showFreq").getFields();
                    freq_operators.setData1(counterMap.get(actionMap.get("showFreqInOut").getRetrieve_counterName()),jedis,freq_time);
                    System.out.println(freq_operators.showInOut());
                    break;
                }
                case 10:{
                    string_operators.setData(counterMap.get(actionMap.get("add_str").getSave_counterName()),jedis);
                    System.out.println(string_operators.add());
                    num_operators.setData(counterMap.get(actionMap.get("add_str").getRetrieve_counterName()),jedis);
                    System.out.println(string_operators.show());
                    break;
                }
                case 11:{
                    string_operators.setData(counterMap.get(actionMap.get("delete_str").getSave_counterName()),jedis);
                    System.out.println(string_operators.delete());
                    num_operators.setData(counterMap.get(actionMap.get("delete_str").getRetrieve_counterName()),jedis);
                    System.out.println(string_operators.show());
                    break;
                }
                case 12:{
                    string_operators.setData(counterMap.get(actionMap.get("show_str").getSave_counterName()),jedis);
                    System.out.println(string_operators.show());
                    break;
                }
                case 13:{
                    list_operators.setData(counterMap.get(actionMap.get("add_list").getSave_counterName()),jedis);
                    System.out.println(list_operators.add());
                    list_operators.setData(counterMap.get(actionMap.get("add_list").getRetrieve_counterName()),jedis);
                    System.out.println(list_operators.show());
                    break;
                }
                case 14:{
                    list_operators.setData(counterMap.get(actionMap.get("show_list").getSave_counterName()),jedis);
                    System.out.println(list_operators.show());
                    break;
                }
                case 15:{
                    set_operators.setData(counterMap.get(actionMap.get("add_set").getSave_counterName()),jedis);
                    System.out.println(set_operators.add());
                    set_operators.setData(counterMap.get(actionMap.get("add_set").getRetrieve_counterName()),jedis);
                    System.out.println(set_operators.show());
                    break;
                }
                case 16:{
                    set_operators.setData(counterMap.get(actionMap.get("show_set").getSave_counterName()),jedis);
                    System.out.println(set_operators.show());
                    break;
                }
                case 17:{
                    zset_operators.setData(counterMap.get(actionMap.get("add_zSet").getSave_counterName()),jedis);
                    System.out.println(zset_operators.add());
                    zset_operators.setData(counterMap.get(actionMap.get("add_zSet").getRetrieve_counterName()),jedis);
                    System.out.println(zset_operators.show());
                    break;
                }
                case 18:{
                    zset_operators.setData(counterMap.get(actionMap.get("show_zSet").getSave_counterName()),jedis);
                    System.out.println(zset_operators.show());
                    break;
                }
            }
        }
    }
    public static void listen() throws Exception { //监听json文件
        File directory = new File("E:\\project\\idea\\redis-demo\\src\\main\\resources\\counters.json");
        FileAlterationObserver observer = new FileAlterationObserver(directory);
        observer.addListener(myfilelistener);
        long interval = 1000;
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval);
        monitor.addObserver(observer);
        monitor.start();
        System.out.println("监听开始");
    }
    public static void main(String[] args) throws Exception {  //redis计数系统
        setData();
        listen();
        System.out.println("Counter计数系统");
        System.out.println("开发者:\n姓名:宋廷泽   学号:18301135");
        String menu = "<1> 显示所有的actions\n";
        menu += "<2> 执行action\n";
        menu += "<0> 退出\n";
        menu += "choice>>";

        System.out.print(menu);
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        while (!choice.equalsIgnoreCase("0")) {
            counterMap = myfilelistener.getCounterMap();
            switch (choice) {
                case "1": {
                    System.out.print(getActions());
                    System.out.println();
                    break;
                }
                case "2": {
                    action_operator();
                    break;
                }
            }
            System.out.print(menu);
            sc = new Scanner(System.in);
            choice = sc.nextLine();
        }
        System.out.println("已退出");
    }
}

