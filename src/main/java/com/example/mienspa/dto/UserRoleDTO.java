package com.example.mienspa.dto;


public class UserRoleDTO extends AbstractDTO<UserRoleDTO>{
	private Integer usrId;
	private String usrUserId;
	private Integer usrRoleId;
	
	
	public UserRoleDTO() {
	}

	public UserRoleDTO(Integer usrId, String usrUserId, Integer usrRoleId) {
		super();
		this.usrId = usrId;
		this.usrUserId = usrUserId;
		this.usrRoleId = usrRoleId;
	}

	public Integer getUsrId() {
		return usrId;
	}

	public void setUsrId(Integer usrId) {
		this.usrId = usrId;
	}

	public String getUsrUserId() {
		return usrUserId;
	}

	public void setUsrUserId(String usrUserId) {
		this.usrUserId = usrUserId;
	}

	public Integer getUsrRoleId() {
		return usrRoleId;
	}

	public void setUsrRoleId(Integer usrRoleId) {
		this.usrRoleId = usrRoleId;
	}
	
	
	
}
