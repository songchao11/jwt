package com.song.controller;

import com.song.mapper.UserMapper;
import com.song.model.ResultDto;
import com.song.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by song on 2018/8/10.
 */
@RestController
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultDto login(@RequestParam String username, @RequestParam String password){
        ResultDto resultDto = new ResultDto();
        String realPassword = userMapper.getPassword(username);
        if(realPassword == null){
            return resultDto.failed("用户名错误!");
        }else if(!realPassword.equals(password)){
            return resultDto.failed("密码错误!");
        }else{
            resultDto.setMessage(JWTUtil.createToken(username));
            return resultDto.success();
        }
    }

}
