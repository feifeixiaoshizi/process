// IMyAidlInterface.aidl
package com.test.process.process;

// Declare any non-default types here with import statements
/*
public void showEntity(com.test.process.process.Entity entity) throws android.os.RemoteException;
public com.test.process.process.Entity getEntity() throws android.os.RemoteException;

解释为啥Entity.aidl和IMyAidlInterface.aidl在同一个包下依然需要导包？
根据最终生成的代码可以看出
public com.test.process.process.Entity getEntity() throws android.os.RemoteException;
在方法里面要使用的是类的全路径名称，生成的类里不能自动导入包，所以在这里就应该给方法参数准备好全路径名的类。

*/
import com.test.process.process.Entity;

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

            void showName ();
            void showEntity (in Entity entity);
            Entity getEntity ();
}
