package operator;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

final class MyFileListener implements FileAlterationListener {  //实现对json文件的监听
    private static final readJson readjson = new readJson();
    private static HashMap<String, CounterSpec> counterMap;
    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {
      //  System.out.println("monitor start scan files..");
        try {
            counterMap = readjson.readCounter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDirectoryCreate(File file) {
        System.out.println(file.getName()+" director created.");
    }


    @Override
    public void onDirectoryChange(File file) {
        System.out.println(file.getName()+" director changed.");
    }


    @Override
    public void onDirectoryDelete(File file) {
        System.out.println(file.getName()+" director deleted.");
    }


    @Override
    public void onFileCreate(File file) {
        System.out.println(file.getName()+" created.");
    }


    @Override
    public void onFileChange(File file) {
        System.out.println(file.getName()+" changed.");
    }


    @Override
    public void onFileDelete(File file) {
        System.out.println(file.getName()+" deleted.");
    }


    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {
     //   System.out.println("monitor stop scanning..");
    }

    public  HashMap<String, CounterSpec> getCounterMap() {
        return counterMap;
    }
}
