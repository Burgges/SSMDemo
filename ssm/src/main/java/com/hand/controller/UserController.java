package com.hand.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.hand.annotation.OperationLog;
import com.hand.dto.JsGridResult;
import com.hand.dto.LoginDto;
import com.hand.dto.MessageDto;
import com.hand.model.User;
import com.hand.service.UserService;
import com.hand.util.AccessUtil;
import com.hand.util.MessageUtil;
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

    private AccessUtil accessUtil = new AccessUtil();

    private MessageUtil<User> messageUtil = new MessageUtil<>();

    /**
     * Add user
     * @param user new user
     * @return return info
     */
    @OperationLog("Add user")
    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    @ResponseBody
    public MessageDto<User> save(User user,
                    HttpServletRequest request) throws Exception {
        MessageDto<User> messageDto;
        Boolean flag = accessUtil.isAccessFlag(request);
        if (!flag) {
            return messageUtil.setMessageDto(500,"No access", user);
        }
        logger.info("insert: /users");
        HttpSession session = request.getSession();
        Integer UID = (Integer)(session.getAttribute("UID") == null ? 0: session.getAttribute("UID"));
        user.setCreatedBy(UID);
        user.setLastUpdatedBy(UID);
        messageDto = userService.save(user);
        return messageDto;
    }

    /**
     * Update user by user id
     * @param params updateUser
     * @param userId primary key
     * @return return info
     * @throws Exception error
     */
    @OperationLog("Update user")
    @RequestMapping(value = "/api/users/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public MessageDto<User> update(@PathVariable Integer userId,
                      @RequestParam Map<String, Object> params,
                        HttpServletRequest request) throws Exception {
        logger.info("update: /users/{userId}");
        String userName = params.get("userName") == null ? "":params.get("userName").toString();
        String userSex = params.get("userSex") == null ? "":params.get("userSex").toString();
        String userMail = params.get("userMail") == null ? "":params.get("userMail").toString();
        User user = new User();
        user.setUserMail(userMail);
        user.setUserName(userName);
        user.setUserSex(userSex);
        HttpSession session = request.getSession();
        Integer UID = (Integer)(session.getAttribute("UID") == null ? 0: session.getAttribute("UID"));
        user.setLastUpdatedBy(UID);
        return userService.update(user, userId);
    }

    /**
     * Delete user by user id
     * @param userId primary key
     * @return return info
     */
    @OperationLog("Delete user")
    @RequestMapping(value = "/api/users/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    public int delete(@PathVariable Integer userId) throws Exception  {
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
    @OperationLog("Select user by user name")
    @RequestMapping(value = "/api/users/{userName}", method = RequestMethod.GET)
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
    @OperationLog("Query all users")
    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    @ResponseBody
    public List<User> queryList(@RequestParam Map<String, Object> params) throws Exception {
        logger.info("queryList: /users");
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
    @RequestMapping(value = "/api/users/index", method = RequestMethod.GET)
    public String toUserIndex() throws Exception  {
        logger.info("toUserIndex: /users/index");
        return "userIndex";
    }

    /**
     * Into login page
     * @return return info
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin() throws Exception {
        logger.info("toLogin: /login");
        return "login";
    }

    /**
     * create captcha
     */
    @RequestMapping(value = "/getCaptcha", method = RequestMethod.GET)
    public void createCaptcha(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
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
     * @param loginDto login user
     * @return return info
     * @throws Exception error
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public MessageDto<User> login(HttpServletRequest request,
                                  LoginDto loginDto) throws Exception {
        MessageDto<User> messageDto = new MessageDto<>();
        String result = userService.login(loginDto, request);
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
    @OperationLog("User log out")
    @RequestMapping(value = "/api/logout", method = RequestMethod.GET)
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
     * Select user by user id
     * @param userId search condition of user id
     * @return return info
     * @throws Exception error
     */
    @OperationLog("Select user by user id")
    @RequestMapping(value = "/api/{userId}/users", method = RequestMethod.GET)
    @ResponseBody
    public User findOneById(@PathVariable Integer userId) throws Exception {
        logger.info("findOneById: /{userId}/users");
        return userService.findOneById(userId);
    }

}
