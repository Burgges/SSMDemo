package com.hand.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 日志类
 * Created by huiyu.chen on 2017/7/15.
 *
 */
public class Log implements Serializable {

    @Id
    @GeneratedValue
    private Integer logId;

    @NotNull
    private Integer userId;

    @NotNull
    private String functionApi;

    private String logDescription;

    private String userIp;

    @NotNull
    private Date operationTime;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }
}
