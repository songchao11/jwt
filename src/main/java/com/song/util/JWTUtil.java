package com.song.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * JWT工具类
 * 主要用来生成Token 和 校验Token
 */
public class JWTUtil {

    //Token 过期时间 (2小时)
    private static final long EXPIRE_TIME = 2 * 60 * 60 * 1000;

    //签名秘钥
    private static final String SECRET = "SHIROJWT";

    /**
     * 生成Token
     */
    public static String createToken(String username){
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.create()
                    .withClaim("username", username)
                    //到期时间
                    .withExpiresAt(date)
                    //创建一个新的jwt，并使用给定算法进行标记
                    .sign(algorithm);
        }catch(Exception e){
            return null;
        }
    }

    /**
     * 校验Token是否正确
     */
    public static boolean verify(String token, String username){
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            //在token中附带了username信息
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            //验证token
            verifier.verify(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * 获取token中的信息，无需secret解密也能获取
     */
    public static String getUsername(String token){
        try{
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        }catch(JWTDecodeException e){
            return null;
        }
    }

}
