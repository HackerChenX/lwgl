package com.hlzt.power.model;

import com.hlzt.commons.model.BaseBean;
/**
 * 用户角色
 * @author user
 *
 */
public class UserRole extends BaseBean {

	private String fkRole;//角色

	private String fkUser;//用户
	
	private String roleName;//角色名

	
	public String getFkRole() {
		return fkRole;
	}

	public void setFkRole(String fkRole) {
		this.fkRole = fkRole;
	}

	public String getFkUser() {
		return fkUser;
	}

	public void setFkUser(String fkUser) {
		this.fkUser = fkUser;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}