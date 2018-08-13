package com.song.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.song.entity.Role;
import com.song.entity.User;
import com.song.entity.UserRole;
import com.song.mapper.RoleMapper;
import com.song.mapper.UserMapper;
import com.song.mapper.UserRoleMapper;
import com.song.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by song on 2018/8/13.
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Set<String> selectUserRoleByUsername(String username) {
        User tbUser = new User();
        tbUser.setUsername(username);
        User realUser = userMapper.selectOne(tbUser);
        EntityWrapper<UserRole> entityWrapper = new EntityWrapper();
        entityWrapper.eq("userId", realUser.getId());
        List<UserRole> userRoles = userRoleMapper.selectList(entityWrapper);
        Set<String> set = new HashSet<>();
        for(UserRole ur : userRoles){
            Role role = roleMapper.selectById(ur.getRoleId());
            set.add(role.getCode());
        }
        return set;
    }
}
