package com.song.controller;

import com.song.dto.ObjectResultDto;
import com.song.dto.request.LoginRequestDto;
import com.song.dto.response.TokenResultDto;
import com.song.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by song on 2018/8/10.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ObjectResultDto<TokenResultDto> login(LoginRequestDto loginRequestDto){
        return userService.login(loginRequestDto);
    }

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public ObjectResultDto<String> getMessage(){
        ObjectResultDto<String> objectResultDto = new ObjectResultDto<>();
        objectResultDto.setData("哈哈");
        return objectResultDto.success();
    }

}
