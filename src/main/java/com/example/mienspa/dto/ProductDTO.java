package com.example.mienspa.dto;

import java.math.BigDecimal;

public class ProductDTO extends AbstractDTO<ProductDTO>{
	private String proId;
	private Integer category_id ;
	private String proName;
	private BigDecimal proPrice;
	private String featureImgPath;
	private String proContent;
	private String proBrand;
	private Boolean isDelete;
	
	
	
	public ProductDTO() {
	}

	public ProductDTO(String proId, Integer category_id, String proName, BigDecimal proPrice, String featureImgPath,
			String proContent, String proBrand, Boolean isDelete) {
		super();
		this.proId = proId;
		this.category_id = category_id;
		this.proName = proName;
		this.proPrice = proPrice;
		this.featureImgPath = featureImgPath;
		this.proContent = proContent;
		this.proBrand = proBrand;
		this.isDelete = isDelete;
	}
	
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public BigDecimal getProPrice() {
		return proPrice;
	}
	public void setProPrice(BigDecimal proPrice) {
		this.proPrice = proPrice;
	}
	public String getFeatureImgPath() {
		return featureImgPath;
	}
	public void setFeatureImgPath(String featureImgPath) {
		this.featureImgPath = featureImgPath;
	}
	public String getProContent() {
		return proContent;
	}
	public void setProContent(String proContent) {
		this.proContent = proContent;
	}
	public String getProBrand() {
		return proBrand;
	}
	public void setProBrand(String proBrand) {
		this.proBrand = proBrand;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	
}
