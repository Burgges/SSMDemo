package com.hand.service.impl;

import com.hand.annotation.OperationLog;
import com.hand.dao.UserMapper;
import com.hand.dto.LoginDto;
import com.hand.dto.MessageDto;
import com.hand.model.User;
import com.hand.service.UserService;
import com.hand.util.MessageUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/6.
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    private MessageUtil<User> messageUtil = new MessageUtil<>();

    @Override
    public MessageDto<User> save(User user) throws Exception {
        logger.info("Insert user");
        Date date = new Date();
        user.setCreationDate(date);
        user.setLastUpdateDate(date);
        user.setPassword("123456");
        if(userMapper.findOneByName(user.getUserName()) != null){
            logger.error("User name was existed: {}",new Exception("User name was existed."));
            return messageUtil.setMessageDto(500,"User name was existed", user);
        }
        userMapper.save(user);
        user = userMapper.findOneByName(user.getUserName());
        return messageUtil.setMessageDto(200,"Insert success", user);
    }

    @Override
    public MessageDto<User> delete(Integer userId) {
        userMapper.delete(userId);
        return null;
    }

    @Override
    public MessageDto<User> update(User user, Integer userId) throws Exception {
        MessageDto<User> messageDto = new MessageDto<>();
        String message = "Update success";
        if(userId == null){
            logger.error("User id not null: {}", new Exception("User id not null"));
            message = "User id not null!";
            return messageUtil.setMessageDto(500,message, user);
        }
        User user2 = userMapper.findOneById(userId);
        if(user2 == null){
            logger.error("User not found: {}", new Exception("User not found"));
            message = "User not found";
            return messageUtil.setMessageDto(500,message, user);
        }
        User user3 = userMapper.findOneByName(user.getUserName());
        if(user3 != null && !user3.getUserId().equals(user2.getUserId())){
            logger.error("User name was existed: {}",new Exception("User name was existed."));
            message = "User name was existed.";
            return messageUtil.setMessageDto(500,message, user);
        }
        user.setPassword("123456");
        Date date = new Date();
        user.setLastUpdateDate(date);
        user.setUserId(userId);
        userMapper.update(user);
        return messageUtil.setMessageDto(200,message, user);
    }

    @Override
    public User findOneById(Integer userId) throws Exception {
        if(userId == null){
            logger.error("User id not null: {}", new Exception("User id not null"));
            return null;
        }
        User user = userMapper.findOneById(userId);
        if(user == null){
            logger.error("User not found: {}", new Exception("User not found"));
            return null;
        }
        return user;
    }

    @Override
    public User findOneByName(String userName) throws Exception {
        if(userName == null || "".equals(userName)){
            logger.error("User name not null: {}", new Exception("User name not null"));
            return null;
        }
        User user = userMapper.findOneByName(userName);
        if(user == null){
            logger.error("User not found: {}", new Exception("User not found"));
            return user;
        }
        return user;
    }

    @Override
    public List<User> findList(Map<String, Object> map) {
        return userMapper.findList(map);
    }

    @OperationLog("User login")
    @Override
    public String login(LoginDto loginDto, HttpServletRequest request) throws Exception {
        String result = "login success";
        HttpSession session = request.getSession();
        String captchaCode = (String) session.getAttribute("captchaCode");
        if ("".equals(captchaCode) || captchaCode == null) {
            logger.error("CaptchaCode was invalid: {}",new Exception("CaptchaCode was invalid"));
            return "CaptchaCode was invalid";
        }
        User user = userMapper.findOneByName(loginDto.getUserName());
        if (user == null) {
            logger.error("User not found: {}",new Exception("User not found"));
            return "User not found";
        }
        if(!user.getPassword().equals(loginDto.getPassword())){
            logger.error("Password error: {}",new Exception("Password error"));
            return "Password error";
        }
        if (!captchaCode.toUpperCase().equals(loginDto.getCaptchaCode().toUpperCase())){
            logger.error("CaptchaCode error: {}",new Exception("CaptchaCode error"));
            return "CaptchaCode error";
        }
        session.setAttribute("userName", user.getUserName());
        session.setAttribute("UID", user.getUserId());
        return result;
    }
}
