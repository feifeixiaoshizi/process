package com.test.process.process;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

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
        //从parcel中读取数据
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
