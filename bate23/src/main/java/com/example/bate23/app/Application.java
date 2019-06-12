package com.example.bate23.app;


import org.xutils.x;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this); // 这一步之后, 我们就可以在任何地方使用x.app()来获取Application的实例了.
        x.Ext.setDebug(true); // 是否输出debug日志
    }
}
