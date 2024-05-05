package com.example.utils;

/** 使用ThreadLocal存储共享的数据变量，如登录的用户信息 */
public class UserUtils {
    private static final ThreadLocal<String> userLocal = new ThreadLocal<>();

    public static String getUserId() {
        return userLocal.get();
    }

    public static void setUserId(String userId) {
        userLocal.set(userId);
    }

    public static void clear() {
        userLocal.remove();
    }
}
