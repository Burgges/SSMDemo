package com.hand.service;

import com.hand.dto.MessageDto;
import com.hand.model.Role;

import java.util.List;
import java.util.Map;

/**
 * Created by huiyu.chen on 2017/7/18.
 *
 */
public interface RoleService {

    /**
     * Insert role
     * @param role new role
     * @return return info
     */
    MessageDto<Role> save(Role role);

    /**
     * Delete role by id
     * @param roleId condition of id
     * @return return info
     */
    MessageDto<Role> delete(Integer roleId);

    /**
     * Update role
     * @param role new role
     * @return return info
     */
    MessageDto<Role> update(Role role);

    /**
     * Select roles by condition
     * @param params condition
     * @return result list
     */
    List<Role> selectRole(Map<String, Object> params);

    /**
     * Get one role by role id
     * @param roleId condition of id
     * @return result role
     */
    Role getRoleById(Integer roleId);

    /**
     * Get one role by role name
     * @param roleName condition of name
     * @return result role
     */
    Role getRoleByName(String roleName);
}
