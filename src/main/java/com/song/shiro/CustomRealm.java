package com.song.shiro;

import com.song.mapper.UserMapper;
import com.song.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义Realm
 */
@Component
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    public boolean supports(AuthenticationToken token){
        return token instanceof JWTToken;
    }

    /**
     * 用户权限校验
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("=====权限认证=====");
        String username = JWTUtil.getUsername(principals.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取该用户角色

        return info;
    }

    /**
     * 用户名正确与否校验
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("=====身份认证方法=====");
        String token = (String) authenticationToken.getCredentials();
        //解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if(username == null || !JWTUtil.verify(token, username)){
            throw new AuthenticationException("token认证失败!");
        }
        String password = userMapper.getPassword(username);
        if(password == null){
            throw new AuthenticationException("用户不存在!");
        }

        return new SimpleAuthenticationInfo(token, token, "myRealm");
    }
}
