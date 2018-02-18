package com.test.process.client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.test.process.process.Entity;
import com.test.process.process.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "clientMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent bindService = new Intent();
        bindService.setAction("com.test.service");
        bindService.setPackage("com.test.process.process");

        //隐式绑定Service对象
        bindService(bindService, serviceConnection, BIND_AUTO_CREATE);

    }

    ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "componentName:" + componentName.getClassName() + "iBinder:" + iBinder);
            //componentName:com.test.process.process.service.RemoteService
            //iBinder:android.os.BinderProxy@ca99aa0

            Log.d(TAG, "ThreadName:" + Thread.currentThread().getName());
            //ThreadName:main 运行在主线程
            IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
            try {
                for (int i = 0; i < 10; i++) {
                    iMyAidlInterface.showName();

                   /*
            远程进程中Service中IMyAidlInterface.Stub实现类的showName方法代码如下：
            @Override
            public void showName() throws RemoteException {
                Log.d(TAG,"threadName:"+Thread.currentThread().getName());
            }
            打印的日志如下：
                    01-03 10:48:13.045 29320-29333/com.test.process.process D/RemoteService: threadName:Binder:29320_2
                    01-03 10:48:13.046 29320-29351/com.test.process.process D/RemoteService: threadName:Binder:29320_3
                    01-03 10:48:13.046 29320-29366/com.test.process.process D/RemoteService: threadName:Binder:29320_5
                    01-03 10:48:13.046 29320-29353/com.test.process.process D/RemoteService: threadName:Binder:29320_4
                    01-03 10:48:13.047 29320-29378/com.test.process.process D/RemoteService: threadName:Binder:29320_6
                    01-03 10:48:13.047 29320-29332/com.test.process.process D/RemoteService: threadName:Binder:29320_1
                    01-03 10:48:13.047 29320-29333/com.test.process.process D/RemoteService: threadName:Binder:29320_2
                    01-03 10:48:13.047 29320-29351/com.test.process.process D/RemoteService: threadName:Binder:29320_3
                    01-03 10:48:13.048 29320-29366/com.test.process.process D/RemoteService: threadName:Binder:29320_5
                    01-03 10:48:13.048 29320-29353/com.test.process.process D/RemoteService: threadName:Binder:29320_4

            根据日志分析可得，调用远程进程中的接口的方法，使用的是Binder驱动提供的线程池中的子线程，而不是主线程。Service的高并发能力是由Binder驱动
            提供的。其实Service主要是提供了进程间交互的核心对象Binder对象，具体调用方法实际上是由Binder来通过Binder驱动来完成的。
*/

                    Entity entity = new Entity("client"+i);
                    iMyAidlInterface.showEntity(entity);
                    Entity entity1 = iMyAidlInterface.getEntity();
                    Log.d(TAG,"entity1:"+entity1.getName());

                }


            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
