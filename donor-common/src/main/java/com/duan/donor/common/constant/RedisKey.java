package com.duan.donor.common.constant;

/**
 * redis key
 */
public enum RedisKey {
    USER_NAME("user_name", 0),
    USER_PHONE("user_phone", 0),
    USER_LOGIN_TOKEN("user_login_token_", 60 * 60 * 24 * 30);

    private final String key;
    private final int expire;

    RedisKey(String key, int expire) {
        this.key = key;
        this.expire = expire;
    }

    public String getKey() {
        return key;
    }

    public int getExpire() {
        return expire;
    }
}