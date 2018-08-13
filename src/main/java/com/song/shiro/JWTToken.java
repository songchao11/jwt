package com.song.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created by song on 2018/8/10.
 */
public class JWTToken implements AuthenticationToken {

    private String token;

    public JWTToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
