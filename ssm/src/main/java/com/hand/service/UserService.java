package com.hand.service;

import com.hand.dto.UserDto;
import com.hand.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/6.
 *
 */
public interface UserService {

    /**
     * create user
     * @param user create object
     * @return return info
     */
    User save(User user);

    /**
     * delete user by userId
     * @param userId user id
     */
    void delete(Integer userId);

    /**
     * update user by userId
     * @param user new user
     * @param userId user Id
     */
    void update(User user, Integer userId) throws Exception;

    /**
     * select user by userId
     * @param userId condition of userId
     * @return result user
     */
    User findOneById(Integer userId) throws Exception;

    /**
     * select user by user name
     * @param userName condition of userId
     * @return result user
     */
    User findOneByName(String userName) throws Exception;

    /**
     * select user list by user properties
     * @param map condition of user properties
     * @return user list
     */
    List<User> findList(Map<String, Object> map);

    String login(UserDto userDto, HttpServletRequest request) throws Exception;
}
