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
 * 同一个应用不同的进程，该服务自己一个独立的进程。
 */

public class OtherProcessService extends Service {
    private  static final String TAG = "OtherProcessService";
    private Binder binder;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind的方法执行了！");
       return binder;
    }


    //如果是单线程的话，多个请求同时来的时候，是逐个处理的。不能同时处理，只能处理完一个请求后才能处理下一个请求。（*****）
    //单线程好比是垂直方向的执行，多线程好比是水平方向的执行。
    //多个线程的话就可以同时执行了，当执行任务1的时候cpu空闲了，就可以给线程2使用来执行任务2了，提高了cpu的利用率。而单线程的时候，在执行任务1的时候
    //虽然cpu空闲了但是依然得等待任务1完成后，才能执行任务2，浪费了cpu资源。


    //因为默认是在主线程中执行，当没有提供子线程的时候，在多个请求同时来时，会在主线程中逐个进行执行，不能在同一时间内全部执行。（******）
    //也可以通过HandlerThread把请求从主线程中移交给一个子线程来逐个执行。单线程多任务，先放入到对列里面，然后再从对列里面逐个取出执行。（*****）
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand:"+intent.getIntExtra("name",-1));
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        int pid = Process.myPid();
        String processName = getProcessName(this,pid);
        Log.d(TAG,"pid:"+pid+"processName:"+processName+"uid:"+Process.myUid());
        //当前服务的进程：  pid:21842  processName:com.test.process.process:otherService    uid:10529
        //当前应用的主进程：pid:21720  processName:com.test.process.processu                id:10529

        binder = new IMyAidlInterface.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override
            public void showName() throws RemoteException {
                Log.d(TAG,"threadName:"+Thread.currentThread().getName());
            }

            @Override
            public void showEntity(Entity entity) throws RemoteException {
                Log.d(TAG,"threadName:"+Thread.currentThread().getName()+"entityName:"+entity.getName());
            }

            @Override
            public Entity getEntity() throws RemoteException {
                Entity entity = new Entity("remote");
                return entity;
            }
        };
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
}
