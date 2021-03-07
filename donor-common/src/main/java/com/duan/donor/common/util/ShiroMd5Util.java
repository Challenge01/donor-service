package com.duan.donor.common.util;

import org.apache.shiro.crypto.hash.SimpleHash;

public class ShiroMd5Util {
    private static String hashAlgorithmName = "MD5"; //加密类型
    private static int hashIterations = 1024; //加密次数

    public static String encrypt(String password) {
        String pwd = new SimpleHash(hashAlgorithmName, password, null, hashIterations).toString();
        return pwd;
    }

    public static void main(String[] args) {
        String encrypt = encrypt("abc123");
        System.out.println(encrypt);
    }
}

