package com.song.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.song.dto.ListResultDto;
import com.song.dto.ObjectResultDto;
import com.song.dto.ResultDto;
import com.song.dto.request.LoginRequestDto;
import com.song.dto.request.SignUpRequestDto;
import com.song.dto.response.TokenResultDto;
import com.song.dto.response.UserResultDto;
import com.song.entity.User;
import com.song.entity.UserRole;
import com.song.enums.UCenterResultEnum;
import com.song.mapper.UserMapper;
import com.song.mapper.UserRoleMapper;
import com.song.service.UserService;
import com.song.util.DefaultPasswordGenerator;
import com.song.util.DefaultSaltGenerator;
import com.song.util.JWTUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by song on 2018/8/13.
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
//    @Autowired
//    private ModelMapper modelMapper;

    @Override
    public ObjectResultDto<TokenResultDto> login(LoginRequestDto requestDto) {
        ObjectResultDto<TokenResultDto> objectResultDto = new ObjectResultDto<>();

        User tbUser = new User();
        tbUser.setUsername(requestDto.getUsername());
        User realUser = userMapper.selectOne(tbUser);

        if(realUser.getPassword() == null || "".equals(realUser.getPassword())){
            return objectResultDto.makeResult(UCenterResultEnum.USERNAME_OR_PASSWORD_ERROR.getValue(), UCenterResultEnum.USERNAME_OR_PASSWORD_ERROR.getComment());
        }else if(!realUser.getPassword().equals(DefaultPasswordGenerator.generateEncryptPassword(
                requestDto.getPassword(), realUser.getPhoneNumber()+realUser.getSalt()))){
            return objectResultDto.makeResult(UCenterResultEnum.USERNAME_OR_PASSWORD_ERROR.getValue(), UCenterResultEnum.USERNAME_OR_PASSWORD_ERROR.getComment());
        }else{
            TokenResultDto tokenResultDto = new TokenResultDto();
            tokenResultDto.setUsername(requestDto.getUsername());
            tokenResultDto.setToken(JWTUtil.createToken(requestDto.getUsername()));
            objectResultDto.setData(tokenResultDto);
            return objectResultDto.success();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultDto signUp(SignUpRequestDto requestDto) {
        ResultDto resultDto = new ResultDto();
        try {
            User userByUsername = new User();
            userByUsername.setUsername(requestDto.getUsername());
            User existUser = userMapper.selectOne(userByUsername);
            if (existUser != null) {
                return resultDto.makeResult(UCenterResultEnum.USER_NAME_EXISTS.getValue(), UCenterResultEnum.USER_NAME_EXISTS.getComment());
            } else {
                User userByPhone = new User();
                userByPhone.setPhoneNumber(requestDto.getPhoneNumber());
                existUser = userMapper.selectOne(userByPhone);
                if (existUser != null) {
                    return resultDto.makeResult(UCenterResultEnum.USER_PHONE_EXISTS.getValue(), UCenterResultEnum.USER_PHONE_EXISTS.getComment());
                }
            }
            //密码加密
            String salt = DefaultSaltGenerator.generate();
            String encryptPassword = DefaultPasswordGenerator.generateEncryptPassword(requestDto.getPassword(), requestDto.getPhoneNumber() + salt);

            User newUser = new User();
            newUser.setUsername(requestDto.getUsername());
            newUser.setPassword(encryptPassword);
            newUser.setPhoneNumber(requestDto.getPhoneNumber());
            newUser.setSalt(salt);

            int retVal = userMapper.insert(newUser);
            if (retVal == 1) {
                existUser = userMapper.selectOne(newUser);
                if (existUser == null) {
                    return resultDto.makeResult(UCenterResultEnum.USER_REGISTER_FAIL.getValue(), UCenterResultEnum.USER_REGISTER_FAIL.getComment());
                }
                UserRole userRole = new UserRole();
                //默认是普通用户
                userRole.setRoleId(1L);
                userRole.setUserId(existUser.getId());
                userRoleMapper.insert(userRole);
                return resultDto.success();
            }
        }catch(Exception e){
            LOGGER.error("注册失败");
            return resultDto.makeResult(UCenterResultEnum.USER_REGISTER_FAIL.getValue(), UCenterResultEnum.USER_REGISTER_FAIL.getComment());
        }
        return resultDto;
    }

    @Override
    public ListResultDto<UserResultDto> getUserList() {
        ListResultDto<UserResultDto> listResultDto = new ListResultDto<>();
        EntityWrapper entityWrapper = new EntityWrapper();
        List<User> users = userMapper.selectList(entityWrapper);
        List<UserResultDto> userResultDtos = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for(User u : users){
            UserResultDto userResultDto = modelMapper.map(u, UserResultDto.class);
            userResultDtos.add(userResultDto);
        }
        listResultDto.setItems(userResultDtos);
        return listResultDto.success();
    }
}
