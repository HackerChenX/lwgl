package com.hlzt.power.model;

import java.util.Date;

import com.hlzt.commons.model.BaseBean;

public class Permission extends BaseBean{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

    private String permName;//权限名

    private String description;//权限描述
    
    private String rpId;//自定义属性，指向角色权限id

	
  	public String getRpId() {
  		return rpId;
  	}

  	public void setRpId(String rpId) {
  		this.rpId = rpId;
  	}
    
 

    public String getPermName() {
		return permName;
	}

	public void setPermName(String permName) {
		this.permName = permName;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}