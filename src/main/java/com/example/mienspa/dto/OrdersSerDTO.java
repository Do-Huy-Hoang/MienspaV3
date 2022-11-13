package com.example.mienspa.dto;

public class OrdersSerDTO extends AbstractDTO<OrdersSerDTO>{
	private String orSerId;
	private String orSerUserId ;
	private String orSerPhoneNo;
	private String orSerStatus;
	private String orSerNote;
	private String[] listSerId;
	
	public OrdersSerDTO() {
		super();
	}

	public OrdersSerDTO(String orSerId, String orSerUserId, String orSerPhoneNo, String orSerStatus, String orSerNote) {
		super();
		this.orSerId = orSerId;
		this.orSerUserId = orSerUserId;
		this.orSerPhoneNo = orSerPhoneNo;
		this.orSerStatus = orSerStatus;
		this.orSerNote = orSerNote;
	}

	public String getOrSerId() {
		return orSerId;
	}

	public void setOrSerId(String orSerId) {
		this.orSerId = orSerId;
	}

	public String getOrSerUserId() {
		return orSerUserId;
	}

	public void setOrSerUserId(String orSerUserId) {
		this.orSerUserId = orSerUserId;
	}

	public String getOrSerPhoneNo() {
		return orSerPhoneNo;
	}

	public void setOrSerPhoneNo(String orSerPhoneNo) {
		this.orSerPhoneNo = orSerPhoneNo;
	}

	public String getOrSerStatus() {
		return orSerStatus;
	}

	public void setOrSerStatus(String orSerStatus) {
		this.orSerStatus = orSerStatus;
	}

	public String getOrSerNote() {
		return orSerNote;
	}

	public void setOrSerNote(String orSerNote) {
		this.orSerNote = orSerNote;
	}

	public String[] getListSerId() {
		return listSerId;
	}

	public void setListSerId(String[] listSerId) {
		this.listSerId = listSerId;
	}	
	
}
