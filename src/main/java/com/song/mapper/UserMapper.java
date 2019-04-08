package com.song.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.song.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by song on 2018/8/10.
 */
public interface UserMapper extends BaseMapper<User> {

}
