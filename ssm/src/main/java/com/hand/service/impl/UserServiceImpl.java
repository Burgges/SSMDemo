package com.hand.service.impl;

import com.hand.dao.UserMapper;
import com.hand.dto.UserDto;
import com.hand.model.User;
import com.hand.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
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

//    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public User save(User user) {
        logger.info("Insert user");
        Date date = new Date();
        user.setCreationDate(date);
        user.setLastUpdateDate(date);
        user.setPassword("123456");
        userMapper.save(user);
        user = userMapper.findOneByName(user.getUserName());
        return user;
    }

    @Override
    public void delete(Integer userId) {
        userMapper.delete(userId);
    }

    @Override
    public void update(User user, Integer userId) throws Exception {
        if(userId == null){
            logger.error("User id not null!");
            throw new Exception("User id not null!");
        }
        User user2 = userMapper.findOneById(userId);
        if(user2 == null){
            logger.error("User not found");
            throw new Exception("User not found!");
        }
        user.setPassword("123456");
        Date date = new Date();
        user.setLastUpdateDate(date);
        user.setUserId(userId);
        userMapper.update(user);
    }

    @Override
    public User findOneById(Integer userId) throws Exception {
        if(userId == null){
            logger.error("User id not null!");
            throw new Exception("User id not null!");
        }
        User user = userMapper.findOneById(userId);
        if(user == null){
            logger.error("User not found!");
            throw new Exception("User not found!");
        }
        return user;
    }

    @Override
    public User findOneByName(String userName) throws Exception {
        if(userName == null || "".equals(userName)){
            logger.error("User name not null!");
            throw new Exception("User name not null!");
        }
        User user = userMapper.findOneByName(userName);
        if(user == null){
            logger.error("User not found!");
            throw new Exception("User not found!");
        }
        return user;
    }

    @Override
    public List<User> findList(Map<String, Object> map) {
        return userMapper.findList(map);
    }

    @Override
    public String login(UserDto userDto, HttpServletRequest request) throws Exception {
        String result = "login success";
        HttpSession session = request.getSession();
        String captchaCode = (String) session.getAttribute("captchaCode");
        if ("".equals(captchaCode) || captchaCode == null) {
            logger.error("CaptchaCode was invalid",new Exception("CaptchaCode was invalid"));
            return "CaptchaCode was invalid";
        }
        User user = userMapper.findOneByName(userDto.getUserName());
        if (user == null) {
            logger.error("User not found",new Exception("User not found"));
            return "User not found";
        }
        if(!user.getPassword().equals(userDto.getPassword())){
            logger.error("Password error",new Exception("Password error"));
            return "Password error";
        }
        if (!captchaCode.equals(userDto.getCaptchaCode())){
            logger.error("CaptchaCode error",new Exception("CaptchaCode error"));
            return "CaptchaCode error";
        }
        return result;
    }
}
