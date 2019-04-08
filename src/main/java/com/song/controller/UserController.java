package com.song.controller;

import com.song.dto.ListResultDto;
import com.song.dto.ObjectResultDto;
import com.song.dto.ResultDto;
import com.song.dto.request.LoginRequestDto;
import com.song.dto.request.SignUpRequestDto;
import com.song.dto.response.TokenResultDto;
import com.song.dto.response.UserResultDto;
import com.song.enums.UCenterResultEnum;
import com.song.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by song on 2018/8/10.
 */
@Api(value = "用户接口", description = "用户接口")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ObjectResultDto<TokenResultDto> login(LoginRequestDto requestDto){
        ObjectResultDto objectResultDto = new ObjectResultDto();
        if(requestDto.getUsername() == null || "".equals(requestDto.getUsername())){
            return objectResultDto.makeResult(UCenterResultEnum.USER_NAME_EMPTY.getValue(), UCenterResultEnum.USER_NAME_EMPTY.getComment());
        }
        if(requestDto.getPassword() == null || "".equals(requestDto.getPassword())){
            return objectResultDto.makeResult(UCenterResultEnum.USER_PASSWORD_EMPTY.getValue(), UCenterResultEnum.USER_PASSWORD_EMPTY.getComment());
        }
        return userService.login(requestDto);
    }

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public ObjectResultDto<String> getMessage(){
        ObjectResultDto<String> objectResultDto = new ObjectResultDto<>();
        objectResultDto.setData("哈哈");
        return objectResultDto.success();
    }

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResultDto signup(SignUpRequestDto requestDto){
        ResultDto resultDto = new ResultDto();
        if(requestDto.getUsername() == null || "".equals(requestDto.getUsername())){
            return resultDto.makeResult(UCenterResultEnum.USER_NAME_EMPTY.getValue(), UCenterResultEnum.USER_NAME_EMPTY.getComment());
        }
        if(requestDto.getPassword() == null || "".equals(requestDto.getPassword())){
            return resultDto.makeResult(UCenterResultEnum.USER_PASSWORD_EMPTY.getValue(), UCenterResultEnum.USER_PASSWORD_EMPTY.getComment());
        }
        String regx = "^[a-zA-Z0-9\\W]{6,20}$";
        if(!requestDto.getPassword().matches(regx) || !requestDto.getConfirmedPassword().matches(regx)){
            return resultDto.makeResult(UCenterResultEnum.USER_PASSWORD_LENGTH_FAIL.getValue(), UCenterResultEnum.USER_PASSWORD_LENGTH_FAIL.getComment());
        }
        if(!requestDto.getPassword().equals(requestDto.getConfirmedPassword())){
            return resultDto.makeResult(UCenterResultEnum.PASSWORD_DISMATCH_ERROR.getValue(), UCenterResultEnum.PASSWORD_DISMATCH_ERROR.getComment());
        }
        if(requestDto.getPhoneNumber() == null || "".equals(requestDto.getPhoneNumber())){
            return resultDto.makeResult(UCenterResultEnum.USER_PHONENUMBER_EMPTY.getValue(), UCenterResultEnum.USER_PHONENUMBER_EMPTY.getComment());
        }
        return userService.signUp(requestDto);
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public ListResultDto<UserResultDto> getUserList(){
        return userService.getUserList();
    }

}
