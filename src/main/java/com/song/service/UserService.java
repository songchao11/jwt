package com.song.service;

import com.baomidou.mybatisplus.service.IService;
import com.song.dto.ListResultDto;
import com.song.dto.ObjectResultDto;
import com.song.dto.ResultDto;
import com.song.dto.request.LoginRequestDto;
import com.song.dto.request.SignUpRequestDto;
import com.song.dto.response.TokenResultDto;
import com.song.dto.response.UserResultDto;
import com.song.entity.User;

/**
 * Created by song on 2018/8/13.
 */
public interface UserService extends IService<User> {

    ObjectResultDto<TokenResultDto> login(LoginRequestDto loginRequestDto);

    ResultDto signUp(SignUpRequestDto requestDto);

    ListResultDto<UserResultDto> getUserList();

}
