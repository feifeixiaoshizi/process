package com.test.process.process.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.test.process.process.Entity;
import com.test.process.process.IMyAidlInterface;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3 0003.
 * 带多线程的Service，每次onStartCommand接收到命令后都开启一个子线程去处理。
 *
 *
 */

public class MultThreadService extends Service {
    private  static final String TAG = "MultThreadService";

    //如果是单线程的话，多个请求同时来的时候，是逐个处理的。不能同时处理，只能处理完一个请求后才能处理下一个请求。（*****）
    //单线程好比是垂直方向的执行，多线程好比是水平方向的执行。
    //多个线程的话就可以同时执行了，当执行任务1的时候cpu空闲了，就可以给线程2使用来执行任务2了，提高了cpu的利用率。而单线程的时候，在执行任务1的时候
    //虽然cpu空闲了但是依然得等待任务1完成后，才能执行任务2，浪费了cpu资源。
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //开启线程解决请求
        processCommand(intent,flags,startId);
        return super.onStartCommand(intent, flags, startId);
    }





    /*

    每次请求来了就直接交给一个新的线程负责处理，主线程负责接受和移交不负责具体的处理。（*****）
02-18 11:09:18.240 27043-27106/com.test.process.process D/MultThreadService: onStartCommand:2
02-18 11:09:18.240 27043-27106/com.test.process.process D/MultThreadService: ThreadName:Thread-6523
02-18 11:09:18.240 27043-27105/com.test.process.process D/MultThreadService: onStartCommand:1
02-18 11:09:18.241 27043-27105/com.test.process.process D/MultThreadService: ThreadName:Thread-6522

根据日志分析：
根据日志打印的时间可以看出两次同时的请求基本是同时执行的，在分别的两个线程中执行的。而且由于线程的随机性，命令2是先于命令1执行了。




*/
    private void processCommand(final  Intent intent,int flags,int startId){
        Thread t= new Thread(){
            @Override
            public void run() {
                Log.d(TAG,"onStartCommand:"+intent.getIntExtra("name",-1));
                try {
                    Log.d(TAG,"ThreadName:"+Thread.currentThread().getName());
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        int pid = Process.myPid();
        String processName = getProcessName(this,pid);
        Log.d(TAG,"pid:"+pid+"processName:"+processName+"uid:"+Process.myUid());


    }

    public String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager)
                cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
