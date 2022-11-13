package com.example.mienspa.dto;

public class ShopInfoDTO extends AbstractDTO<ShopInfoDTO>{
	private String shiId;
	private String shiName;
	private String shiAddress;
	private String shiPhoneNo;
	private String shiBossName;
	private String shiLogoImage;
	private String shiNote;
	private Boolean isDelete;
	
	
	public ShopInfoDTO() {
	}
	public ShopInfoDTO(String shiId, String shiName, String shiAddress, String shiPhoneNo, String shiBossName,
			String shiLogoImage, String shiNote, Boolean isDelete) {
		super();
		this.shiId = shiId;
		this.shiName = shiName;
		this.shiAddress = shiAddress;
		this.shiPhoneNo = shiPhoneNo;
		this.shiBossName = shiBossName;
		this.shiLogoImage = shiLogoImage;
		this.shiNote = shiNote;
		this.isDelete = isDelete;
	}
	public String getShiId() {
		return shiId;
	}
	public void setShiId(String shiId) {
		this.shiId = shiId;
	}
	public String getShiName() {
		return shiName;
	}
	public void setShiName(String shiName) {
		this.shiName = shiName;
	}
	public String getShiAddress() {
		return shiAddress;
	}
	public void setShiAddress(String shiAddress) {
		this.shiAddress = shiAddress;
	}
	public String getShiPhoneNo() {
		return shiPhoneNo;
	}
	public void setShiPhoneNo(String shiPhoneNo) {
		this.shiPhoneNo = shiPhoneNo;
	}
	public String getShiBossName() {
		return shiBossName;
	}
	public void setShiBossName(String shiBossName) {
		this.shiBossName = shiBossName;
	}
	public String getShiLogoImage() {
		return shiLogoImage;
	}
	public void setShiLogoImage(String shiLogoImage) {
		this.shiLogoImage = shiLogoImage;
	}
	public String getShiNote() {
		return shiNote;
	}
	public void setShiNote(String shiNote) {
		this.shiNote = shiNote;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	
}
