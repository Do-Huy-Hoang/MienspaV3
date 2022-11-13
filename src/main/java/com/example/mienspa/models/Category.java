package com.example.mienspa.models;
// Generated Oct 24, 2022, 4:46:04 PM by Hibernate Tools 4.3.6.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Category generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "category", catalog = "mienspa3")
public class Category implements java.io.Serializable {

	private Integer cateId;
	private String cateName;
	private Integer cateIdParent;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isDelete;
	private Set<Product> products = new HashSet<Product>(0);

	public Category() {
	}

	public Category(String cateName) {
		this.cateName = cateName;
	}

	public Category(String cateName, Integer cateIdParent, Date createdAt, Date updatedAt, Boolean isDelete,
			Set<Product> products) {
		this.cateName = cateName;
		this.cateIdParent = cateIdParent;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isDelete = isDelete;
		this.products = products;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "cate_id", unique = true, nullable = false)
	public Integer getCateId() {
		return this.cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	@Column(name = "cate_name", nullable = false, length = 100)
	public String getCateName() {
		return this.cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	@Column(name = "cate_id_parent")
	public Integer getCateIdParent() {
		return this.cateIdParent;
	}

	public void setCateIdParent(Integer cateIdParent) {
		this.cateIdParent = cateIdParent;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", length = 19)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", length = 19)
	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Column(name = "isDelete")
	public Boolean getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	public Set<Product> getProducts() {
		return this.products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}
