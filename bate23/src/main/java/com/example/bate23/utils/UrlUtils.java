package com.example.bate23.utils;

public class UrlUtils {
    //网络端口号
    //公司热点地址
//    private static final String localhost="http://10.102.114.200:9898/finalprogram/";
    //手机热点地址
    private static final String localhost="http://172.20.10.4:9898/finalprogram/";
//    private static final String localhost="http://192.168.93.1:9898/finalprogram/";
    //校园网地址
//    private static final String localhost="http://10.117.192.75:9898/finalprogram/";
    //登录
    public static final String loginurl=localhost+"user/login";
    //注册
    public static final String registurl=localhost+"user/regist";
    //添加一个号码至云端
    public static final String addurl=localhost+"blacklist/add";
    //移除一个号码至云端
    public static final String deleteurl=localhost+"blacklist/add";
    //查询云端所有黑名单
    public static final String queryurl=localhost+"blacklist/query";
    //BUG提交
    public static final String bugsub=localhost+"bugadd/add";
}
