package com.song.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 默认密码生成器
 */
public class DefaultPasswordGenerator {

    /**
     * 加密方式
     */
    private static String algorithmName = "MD5";

    /**
     * 加密次数
     */
    private static int hashIterations = 2;

    public static String generateEncryptPassword(String password, String credentialsSalt){
        String newPassword = new SimpleHash(algorithmName, password, ByteSource.Util.bytes(credentialsSalt), hashIterations).toHex();
        return newPassword;
    }

}
