package com.hand.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.hand.dto.JsGridResult;
import com.hand.dto.MessageDto;
import com.hand.dto.UserDto;
import com.hand.model.User;
import com.hand.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/6.
 *
 */

@Controller
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class);
    @Resource
    private UserService userService;


    @Resource
    private DefaultKaptcha captchaProducer;

    /**
     * Add user
     * @param user new user
     * @return return info
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public int save(User user) {
        logger.info("insert: /users");
        int result = 0;
        user = userService.save(user);
        if(user.getUserId() == null){
            result = -1;
        }

        return result;
    }

    /**
     * Update user by user id
     * @param params updateUser
     * @param userId primary key
     * @return return info
     * @throws Exception error
     */
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public int update(@PathVariable Integer userId,
                      @RequestParam Map<String, Object> params) throws Exception {
        logger.info("update: /users/{userId}");
        int result = -1;
        String userName = params.get("userName") == null ? "":params.get("userName").toString();
        String userSex = params.get("userSex") == null ? "":params.get("userSex").toString();
        String userMail = params.get("userMail") == null ? "":params.get("userMail").toString();
        User user = new User();
        user.setUserMail(userMail);
        user.setUserName(userName);
        user.setUserSex(userSex);
        userService.update(user, userId);
        if(user.getUserId() == null){
            result = 0;
        }
        return result;
    }

    /**
     * Delete user by user id
     * @param userId primary key
     * @return return info
     */
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    public int delete(@PathVariable Integer userId) {
        logger.info("delete: /users/{userId}");
        userService.delete(userId);
        return 1;
    }

    /**
     * Select user by user name
     * @param userName search condition of user name
     * @return return info
     * @throws Exception error
     */
    @RequestMapping(value = "/users/{userName}", method = RequestMethod.GET)
    @ResponseBody
    public User findOneByName(@PathVariable String userName) throws Exception {
        logger.info("findOneByName: /users/{userName}");
        return userService.findOneByName(userName);
    }

    /**
     * Query all user
     * @param params search condition
     * @return return info
     * @throws Exception error
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<User> queryList(@RequestParam Map<String, Object> params,
                                HttpServletRequest request) throws Exception {
        logger.info("queryList: /users");
        logger.info("login user:"+ request.getSession().getAttribute("userName"));

        List<User> userList = userService.findList(params);
        JsGridResult<User> jsGridResult = new JsGridResult<>();
        jsGridResult.setData(userList);
        jsGridResult.setItemsCount(userList.size()+"");
        return userList;
    }

    /**
     * Into user index page
     * @return return info
     */
    @RequestMapping(value = "/users/index", method = RequestMethod.GET)
    public String toUserIndex() {
        logger.info("toUserIndex: /users/index");
        return "userIndex";
    }

    /**
     * Into login page
     * @return return info
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin() {
        logger.info("toLogin: /login");
        return "login";
    }

    /**
     * create captcha
     */
    @RequestMapping(value = "/getCaptcha", method = RequestMethod.GET)
    public void createCaptcha(HttpServletRequest request,
                              HttpServletResponse response) {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream out = null;
        try {
            String capText = captchaProducer.createText();
            HttpSession session = request.getSession();
            session.setAttribute("captchaCode",capText);
            BufferedImage bi = captchaProducer.createImage(capText);
            logger.info("captcha: {}" + bi);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (Exception e) {
            logger.info("create captcha fail: {}", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                }catch (Exception e){
                    logger.info("captcha output close fail: {}",e);
                }
            }
        }
    }

    /**
     * user login
     * @param request request
     * @param userDto login user
     * @return return info
     * @throws Exception error
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public MessageDto login(HttpServletRequest request, UserDto userDto) throws Exception {
        MessageDto messageDto = new MessageDto();
        String result = userService.login(userDto, request);
        Integer code = 500;
        if ("login success".equals(result)) {
            result = "userIndex";
            code = 200;
        }
        messageDto.setCode(code);
        messageDto.setMessage(result);
        return messageDto;
    }

    /**
     * log out
     * @param request request
     * @return result
     * @throws Exception error
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        if(session.getAttribute("userName") == null || "".equals(session.getAttribute("userName"))){
            return "login";
        }
        session.removeAttribute("userName");
        session.removeAttribute("UID");
        return "login";
    }

    /**
     * Into add user page
     * @return return info
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String toUserAdd() {
        logger.info("toUserAdd: /addPage");
        return "userAdd";
    }

    /**
     * Into update user page
     * @return return info
     */
    @RequestMapping(value = "/updatePage", method = RequestMethod.GET)
    public String toUserUpdate() {
        logger.info("toUserUpdate: /updatePage");
        return "userUpdate";
    }

    /**
     * Select user by user id
     * @param userId search condition of user id
     * @return return info
     * @throws Exception error
     */
    @RequestMapping(value = "/{userId}/users", method = RequestMethod.GET)
    @ResponseBody
    public User findOneById(@PathVariable Integer userId) throws Exception {
        logger.info("findOneById: /{userId}/users");
        return userService.findOneById(userId);
    }

}
