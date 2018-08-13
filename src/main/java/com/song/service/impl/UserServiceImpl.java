package com.song.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.song.dto.ObjectResultDto;
import com.song.dto.request.LoginRequestDto;
import com.song.dto.response.TokenResultDto;
import com.song.entity.User;
import com.song.enums.UCenterResultEnum;
import com.song.mapper.UserMapper;
import com.song.service.UserService;
import com.song.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by song on 2018/8/13.
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ObjectResultDto<TokenResultDto> login(LoginRequestDto requestDto) {
        ObjectResultDto<TokenResultDto> objectResultDto = new ObjectResultDto<>();
        if(requestDto.getUsername() == null || "".equals(requestDto.getUsername())){
            return objectResultDto.makeResult(UCenterResultEnum.USER_NAME_EMPTY.getValue(), UCenterResultEnum.USER_NAME_EMPTY.getComment());
        }
        if(requestDto.getPassword() == null || "".equals(requestDto.getPassword())){
            return objectResultDto.makeResult(UCenterResultEnum.USER_PASSWORD_EMPTY.getValue(), UCenterResultEnum.USER_PASSWORD_EMPTY.getComment());
        }

        User tbUser = new User();
        tbUser.setUsername(requestDto.getUsername());
        User realUser = userMapper.selectOne(tbUser);

        if(realUser.getPassword() == null || "".equals(realUser.getPassword())){
            return objectResultDto.makeResult(UCenterResultEnum.USERNAME_OR_PASSWORD_ERROR.getValue(), UCenterResultEnum.USERNAME_OR_PASSWORD_ERROR.getComment());
        }else if(!realUser.getPassword().equals(requestDto.getPassword())){
            return objectResultDto.makeResult(UCenterResultEnum.USERNAME_OR_PASSWORD_ERROR.getValue(), UCenterResultEnum.USERNAME_OR_PASSWORD_ERROR.getComment());
        }else{
            TokenResultDto tokenResultDto = new TokenResultDto();
            tokenResultDto.setUsername(requestDto.getUsername());
            tokenResultDto.setToken(JWTUtil.createToken(requestDto.getUsername()));
            objectResultDto.setData(tokenResultDto);
            return objectResultDto.success();
        }
    }

}
