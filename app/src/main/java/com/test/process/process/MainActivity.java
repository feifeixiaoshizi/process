package com.test.process.process;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent bindService = new Intent();
        bindService.setAction("com.test.service");
        bindService.setPackage("com.test.process.process");

        // bindService(bindService,serviceConnection,BIND_AUTO_CREATE);


        Intent bindOtherService = new Intent();
        bindOtherService.setAction("com.test.otherservice");
        bindOtherService.setPackage("com.test.process.process");


        /*1）每次调用bindService()时，绑定的服务是一样的；
          （2）没有调用unbindService()

          两次绑定的操作，Service的onBind（）方法只会执行一次。（*****）
          */
        bindService(bindOtherService, serviceConnection1, BIND_AUTO_CREATE);
        bindService(bindOtherService, serviceConnection1, BIND_AUTO_CREATE);

        int pid = Process.myPid();
        Log.d(TAG, "pid:" + pid + "uid:" + Process.myUid());
        String processName = getProcessName(this, pid);
        Log.d(TAG, "pid:" + pid + "processName:" + processName + "uid:" + Process.myUid());
        //当前应用的主进程：pid:21720   processName:com.test.process.processu   id:10529


        startService1();
        startService2();


        startIntentService1();
        startIntentService2();



        startMultService1();
        startMultService2();

    }


    private void startService1() {
        Intent bindOtherService = new Intent();
        bindOtherService.setAction("com.test.otherservice");
        bindOtherService.setPackage("com.test.process.process");
        bindOtherService.putExtra("name", 1);
        startService(bindOtherService);


    }

    private void startService2() {
        Intent bindOtherService = new Intent();
        bindOtherService.setAction("com.test.otherservice");
        bindOtherService.setPackage("com.test.process.process");
        bindOtherService.putExtra("name", 2);
        startService(bindOtherService);


    }


    private void startIntentService1() {
        Intent bindOtherService = new Intent();
        bindOtherService.setAction("com.test.intetnservice");
        bindOtherService.setPackage("com.test.process.process");
        bindOtherService.putExtra("name", 1);
        startService(bindOtherService);


    }

    private void startIntentService2() {
        Intent bindOtherService = new Intent();
        bindOtherService.setAction("com.test.intetnservice");
        bindOtherService.setPackage("com.test.process.process");
        bindOtherService.putExtra("name", 2);
        startService(bindOtherService);


    }

    private void startMultService1() {
        Intent bindOtherService = new Intent();
        bindOtherService.setAction("com.test.multservice");
        bindOtherService.setPackage("com.test.process.process");
        bindOtherService.putExtra("name", 1);
        startService(bindOtherService);


    }

    private void startMultService2() {
        Intent bindOtherService = new Intent();
        bindOtherService.setAction("com.test.multservice");
        bindOtherService.setPackage("com.test.process.process");
        bindOtherService.putExtra("name", 2);
        startService(bindOtherService);


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


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "componentName:" + componentName.getClassName() + "iBinder:" + iBinder);
            Log.d(TAG, "ThreadName:" + Thread.currentThread().getName());
            IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
            try {
                iMyAidlInterface.showName();

                Entity entity = new Entity("client");
                iMyAidlInterface.showEntity(entity);
                Entity entity1 = iMyAidlInterface.getEntity();
                Log.d(TAG, "entity1:" + entity1.getName());
                /*
                日志分析：
                RemoteService: threadName:main
                可以看出在同一个进程内调用Service返回的Binder中的方法，Service中Binder的方法是运行在主线程中的，
                但是不同的进程间调用该方法是运行Binder驱动的线程池中。


                */
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    ServiceConnection serviceConnection1 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "componentName:" + componentName.getClassName() + "iBinder:" + iBinder);
            Log.d(TAG, "ThreadName:" + Thread.currentThread().getName());
            IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
            try {
                iMyAidlInterface.showName();

                Entity entity = new Entity("client");
                iMyAidlInterface.showEntity(entity);
                Entity entity1 = iMyAidlInterface.getEntity();
                Log.d(TAG, "entity1:" + entity1.getName());
                /*
                日志分析：
                RemoteService: threadName:main
                可以看出在同一个进程内调用Service返回的Binder中的方法，Service中Binder的方法是运行在主线程中的，
                但是不同的进程间调用该方法是运行Binder驱动的线程池中。


                */
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
