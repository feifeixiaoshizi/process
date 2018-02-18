package com.test.process.process.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.test.process.process.Entity;
import com.test.process.process.IMyAidlInterface;

/**
 * Created by Administrator on 2018/1/3 0003.
 *
 * 远程进程中的服务对象
 *
 */

public class RemoteService extends Service {
    private  static final String TAG = "RemoteService";
    IMyAidlInterface.Stub binder ;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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




}
