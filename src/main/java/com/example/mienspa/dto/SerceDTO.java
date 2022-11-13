package com.example.mienspa.dto;

import java.math.BigDecimal;

public class SerceDTO extends AbstractDTO<SerceDTO>{
	private String seId;
	private String seName;
	private BigDecimal sePrice;
	private String seDescription;
	private String seNote;
	private String seImage;
	private Boolean seTurnOn;
	private Boolean isDelete;
	
	
	
	public SerceDTO() {
	}

	public SerceDTO(String seId, String seName, BigDecimal sePrice, String seDescription, String seNote, String seImage,
			Boolean seTurnOn, Boolean isDelete) {
		super();
		this.seId = seId;
		this.seName = seName;
		this.sePrice = sePrice;
		this.seDescription = seDescription;
		this.seNote = seNote;
		this.seImage = seImage;
		this.seTurnOn = seTurnOn;
		this.isDelete = isDelete;
	}

	public String getSeId() {
		return seId;
	}

	public void setSeId(String seId) {
		this.seId = seId;
	}

	public String getSeName() {
		return seName;
	}

	public void setSeName(String seName) {
		this.seName = seName;
	}

	public BigDecimal getSePrice() {
		return sePrice;
	}

	public void setSePrice(BigDecimal sePrice) {
		this.sePrice = sePrice;
	}

	public String getSeDescription() {
		return seDescription;
	}

	public void setSeDescription(String seDescription) {
		this.seDescription = seDescription;
	}

	public String getSeNote() {
		return seNote;
	}

	public void setSeNote(String seNote) {
		this.seNote = seNote;
	}

	public String getSeImage() {
		return seImage;
	}

	public void setSeImage(String seImage) {
		this.seImage = seImage;
	}

	public Boolean getSeTurnOn() {
		return seTurnOn;
	}

	public void setSeTurnOn(Boolean seTurnOn) {
		this.seTurnOn = seTurnOn;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}	
}
