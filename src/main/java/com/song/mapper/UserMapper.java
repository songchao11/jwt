package com.song.mapper;

import org.springframework.stereotype.Repository;

/**
 * Created by song on 2018/8/10.
 */
@Repository
public interface UserMapper {

    String getPassword(String username);

}
