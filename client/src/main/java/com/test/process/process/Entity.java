package com.test.process.process;

import android.os.Parcel;
import android.os.Parcelable;

/**
 需要建立一个和远程提供服务的应用一样的包名，并把Entity类放入到其中。
 客户端使用的java类要和服务端使用保证同一个包名，同一个类名。
 *
 */

/*
public class Entity implements Parcelable {
    private String name;

    public String getName() {
        return name;
    }

    public Entity(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Entity(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<Entity> CREATOR = new Creator<Entity>() {
        @Override
        public Entity createFromParcel(Parcel in) {
            return new Entity(in);
        }

        @Override
        public Entity[] newArray(int size) {
            return new Entity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);

    }

    //手动实现这个方法
    public void readFromParcel(Parcel dest) {
        //注意，这里的读取顺序要writeToParcel()方法中的写入顺序一样
        name = dest.readString();
    }

}
*/
