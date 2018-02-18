package com.test.process.process.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by jianshengli on 2018/2/18.
 */

public class MyIntentService extends IntentService {
    private String TAG="MyIntentService";
    public MyIntentService(String name) {
        super(name);
    }

    public MyIntentService(){
        super("");

    }


    /*

02-18 11:14:31.197 27411-27443/com.test.process.process D/MyIntentService: onStartCommand:1
02-18 11:14:32.198 27411-27443/com.test.process.process D/MyIntentService: onStartCommand:2

从时间上可以看出，命令2比命令1晚了一分钟执行。

在这里是把命令都存放到了Looper中的消息队列里面，然后单个子线程会从消息队列里依次取出每个命令任务执行，执行完一个后再执行下一个。


*/
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG,"onStartCommand:"+intent.getIntExtra("name",-1));
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
