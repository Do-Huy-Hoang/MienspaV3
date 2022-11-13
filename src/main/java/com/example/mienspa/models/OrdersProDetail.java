package com.example.mienspa.models;
// Generated Oct 24, 2022, 4:46:04 PM by Hibernate Tools 4.3.6.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Ordersprodetail generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ordersprodetail", catalog = "mienspa3")
public class OrdersProDetail implements java.io.Serializable {

	private Integer ordProId;
	private OrdersPro orderspro;
	private Product product;

	public OrdersProDetail() {
	}

	public OrdersProDetail(OrdersPro orderspro, Product product) {
		this.orderspro = orderspro;
		this.product = product;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ordPro_Id", unique = true, nullable = false)
	public Integer getOrdProId() {
		return this.ordProId;
	}

	public void setOrdProId(Integer ordProId) {
		this.ordProId = ordProId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ordPro_OrderId", nullable = false)
	public OrdersPro getOrderspro() {
		return this.orderspro;
	}

	public void setOrderspro(OrdersPro orderspro) {
		this.orderspro = orderspro;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ordPro_ProductId", nullable = false)
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
