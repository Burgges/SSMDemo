package com.hand.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
 * Created by huiyu.chen on 2017/07/06.
 *
 */
public class User extends BaseModel implements Serializable{

    //用户ID
    @Id
    @GeneratedValue
    private Integer userId;
    //用户名
    @NotNull
    private String userName;
    //密码
    @NotNull
    private String password;
    //性别
    private String userSex;
    //邮箱
    private String userMail;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
}
