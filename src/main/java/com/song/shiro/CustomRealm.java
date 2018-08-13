package com.song.shiro;

import com.song.entity.User;
import com.song.mapper.UserMapper;
import com.song.service.UserRoleService;
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

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义Realm
 */
@Component
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleService userRoleService;

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
        //获得该用户角色
//        String role = userMapper.getRole(username);
//        //每个角色拥有默认的权限
//        String rolePermission = userMapper.getRolePermission(username);
//        //每个用户可以设置新的权限
//        String permission = userMapper.getPermission(username);
//        Set<String> roleSet = new HashSet<>();
//        Set<String> permissionSet = new HashSet<>();
//        //需要将 role, permission 封装到 Set 作为 info.setRoles(), info.setStringPermissions() 的参数
//        roleSet.add(role);
//        permissionSet.add(rolePermission);
//        permissionSet.add(permission);
        Set<String> set = userRoleService.selectUserRoleByUsername(username);

        //设置该用户拥有的角色和权限
        info.setRoles(set);
        info.setStringPermissions(set);
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

        User tbUser = new User();
        tbUser.setUsername(username);
        User realUser = userMapper.selectOne(tbUser);

        if(realUser == null){
            throw new AuthenticationException("用户不存在!");
        }
        if (realUser.getDeleted() == 1) {
            throw new AuthenticationException("该用户已被封号！");
        }

        return new SimpleAuthenticationInfo(token, token, "myRealm");
    }
}
