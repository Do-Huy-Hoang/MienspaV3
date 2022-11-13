package com.example.mienspa.dto;


public class OrderSerDetailDTO extends AbstractDTO<OrderSerDetailDTO>{
	private Integer ordSerId;
	private String ordSerOrderId ;
	private String ordSerServiceId ;
	
	
	public OrderSerDetailDTO() {
	}


	public OrderSerDetailDTO(Integer ordSerId, String ordSerOrderId, String ordSerServiceId) {
		super();
		this.ordSerId = ordSerId;
		this.ordSerOrderId = ordSerOrderId;
		this.ordSerServiceId = ordSerServiceId;
	}


	public Integer getOrdSerId() {
		return ordSerId;
	}


	public void setOrdSerId(Integer ordSerId) {
		this.ordSerId = ordSerId;
	}


	public String getOrdSerOrderId() {
		return ordSerOrderId;
	}


	public void setOrdSerOrderId(String ordSerOrderId) {
		this.ordSerOrderId = ordSerOrderId;
	}


	public String getOrdSerServiceId() {
		return ordSerServiceId;
	}


	public void setOrdSerServiceId(String ordSerServiceId) {
		this.ordSerServiceId = ordSerServiceId;
	}
	
}
