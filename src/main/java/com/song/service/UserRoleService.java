package com.song.service;

import com.baomidou.mybatisplus.service.IService;
import com.song.entity.UserRole;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by song on 2018/8/13.
 */
public interface UserRoleService extends IService<UserRole> {
    /**
     * 根据username获取用户角色
     */
    Set<String> selectUserRoleByUsername(String username);
}
