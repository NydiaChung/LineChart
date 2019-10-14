package com.example.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import static java.lang.Thread.sleep;

/**
 * @author david
 */
public class MyService extends Service {

    private boolean serviceRunning = false;
    private boolean isStopThread = false;


    // 创建Service时调用该方法，只调用一次
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("--onCreate()--");
        serviceRunning = true;
        myThread();

    }

    // 每次启动Servcie时都会调用该方法
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("--onStartCommand()--");
        return super.onStartCommand(intent, flags, startId);
    }

    //退出或者销毁时调用该方法
    @Override
    public void onDestroy() {
        System.out.println("--onDestroy()--");
        isStopThread = true;
        super.onDestroy();
    }

    // 解绑Servcie调用该方法
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void myThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //执行操作
                    if (isStopThread) {
                        System.out.println("=========");
                        break;
                    }
                    System.out.println("---Service运行中---");
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public IBinder onBind(Intent intent){
        System.out.println("--onBind()--");
        return new MyBinder();
    }

    public class MyBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }
}
