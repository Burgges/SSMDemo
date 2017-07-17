package com.hand.dto;

import java.util.Date;

/**
 * 前台日志显示信息
 * Created by huiyu.chen on 2017/7/15.
 *
 */
public class LogDto {

    private Integer logId;

    private Integer userId;

    private String functionApi;

    private String logDescription;

    private String userIp;

    private Date operationTime;

    private String userName;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFunctionApi() {
        return functionApi;
    }

    public void setFunctionApi(String functionApi) {
        this.functionApi = functionApi;
    }

    public String getLogDescription() {
        return logDescription;
    }

    public void setLogDescription(String logDescription) {
        this.logDescription = logDescription;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
