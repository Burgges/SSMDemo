package com.hand.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * User role class
 * Created by huiyu.chen on 2017/7/18.
 *
 */
public class Role extends BaseModel {

    @Id
    @GeneratedValue
    private Integer roleId;

    @NotNull(message = "Role name is not null!")
    @Column(unique = true)
    private String roleName;

    private String roleDescription;

    @NotNull(message = "Authority code is not null!")
    private String authorityCode; //权限代码（关联lookupCode中的code）

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getAuthorityCode() {
        return authorityCode;
    }

    public void setAuthorityCode(String authorityCode) {
        this.authorityCode = authorityCode;
    }
}
