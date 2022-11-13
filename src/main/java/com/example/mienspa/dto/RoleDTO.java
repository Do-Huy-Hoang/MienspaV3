package com.example.mienspa.dto;

public class RoleDTO extends AbstractDTO<RoleDTO>{
	private Integer roId;
	private String roName;
	private String roDisplayName;
	
	
	public RoleDTO() {
	}
	public RoleDTO(Integer roId, String roName, String roDisplayName) {
		super();
		this.roId = roId;
		this.roName = roName;
		this.roDisplayName = roDisplayName;
	}
	public Integer getRoId() {
		return roId;
	}
	public void setRoId(Integer roId) {
		this.roId = roId;
	}
	public String getRoName() {
		return roName;
	}
	public void setRoName(String roName) {
		this.roName = roName;
	}
	public String getRoDisplayName() {
		return roDisplayName;
	}
	public void setRoDisplayName(String roDisplayName) {
		this.roDisplayName = roDisplayName;
	}
	
	
}
