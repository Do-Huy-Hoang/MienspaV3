package com.example.mienspa.models;
// Generated Oct 24, 2022, 4:46:04 PM by Hibernate Tools 4.3.6.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Users generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "users", catalog = "mienspa3")
public class Users implements java.io.Serializable {

	private String usId;
	private String usUserName;
	private String usPassword;
	private String usDob;
	private String usAddress;
	private String usPhoneNo;
	private String usEmailNo;
	private String usImage;
	private String usNote;
	private Boolean isAdmin;
	private Date createdAt;
	private Date updatedAt;
	private Boolean isDelete;
	private Set<OrdersPro> orderspros = new HashSet<OrdersPro>(0);
	private Set<RefreshToken> refreshtokens = new HashSet<RefreshToken>(0);
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	private Set<OrdersSer> orderssers = new HashSet<OrdersSer>(0);

	public Users() {
	}

	public Users(String usId, String usUserName, String usPassword, String usEmailNo) {
		this.usId = usId;
		this.usUserName = usUserName;
		this.usPassword = usPassword;
		this.usEmailNo = usEmailNo;
	}

	public Users(String usUserName, String usPassword, String usEmailNo) {
		super();
		this.usUserName = usUserName;
		this.usPassword = usPassword;
		this.usEmailNo = usEmailNo;
	}

	public Users(String usId, String usUserName, String usPassword, String usDob, String usAddress, String usPhoneNo,
			String usEmailNo, String usImage, String usNote, Boolean isAdmin, Date createdAt, Date updatedAt,
			Boolean isDelete, Set<OrdersPro> orderspros, Set<RefreshToken> refreshtokens, Set<UserRole> userRoles,
			Set<OrdersSer> orderssers) {
		this.usId = usId;
		this.usUserName = usUserName;
		this.usPassword = usPassword;
		this.usDob = usDob;
		this.usAddress = usAddress;
		this.usPhoneNo = usPhoneNo;
		this.usEmailNo = usEmailNo;
		this.usImage = usImage;
		this.usNote = usNote;
		this.isAdmin = isAdmin;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isDelete = isDelete;
		this.orderspros = orderspros;
		this.refreshtokens = refreshtokens;
		this.userRoles = userRoles;
		this.orderssers = orderssers;
	}

	@Id

	@Column(name = "us_Id", unique = true, nullable = false, length = 128)
	public String getUsId() {
		return this.usId;
	}

	public void setUsId(String usId) {
		this.usId = usId;
	}

	@Column(name = "us_UserName", nullable = false, length = 120)
	public String getUsUserName() {
		return this.usUserName;
	}

	public void setUsUserName(String usUserName) {
		this.usUserName = usUserName;
	}

	@Column(name = "us_Password", nullable = false, length = 120)
	public String getUsPassword() {
		return this.usPassword;
	}

	public void setUsPassword(String usPassword) {
		this.usPassword = usPassword;
	}

	@Column(name = "us_Dob", length = 20)
	public String getUsDob() {
		return this.usDob;
	}

	public void setUsDob(String usDob) {
		this.usDob = usDob;
	}

	@Column(name = "us_Address", length = 150)
	public String getUsAddress() {
		return this.usAddress;
	}

	public void setUsAddress(String usAddress) {
		this.usAddress = usAddress;
	}

	@Column(name = "us_PhoneNo", length = 15)
	public String getUsPhoneNo() {
		return this.usPhoneNo;
	}

	public void setUsPhoneNo(String usPhoneNo) {
		this.usPhoneNo = usPhoneNo;
	}

	@Column(name = "us_EmailNo", nullable = false, length = 150)
	public String getUsEmailNo() {
		return this.usEmailNo;
	}

	public void setUsEmailNo(String usEmailNo) {
		this.usEmailNo = usEmailNo;
	}

	@Column(name = "us_Image", length = 250)
	public String getUsImage() {
		return this.usImage;
	}

	public void setUsImage(String usImage) {
		this.usImage = usImage;
	}

	@Column(name = "us_Note", length = 65535)
	public String getUsNote() {
		return this.usNote;
	}

	public void setUsNote(String usNote) {
		this.usNote = usNote;
	}

	@Column(name = "is_admin")
	public Boolean getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<OrdersPro> getOrderspros() {
		return this.orderspros;
	}

	public void setOrderspros(Set<OrdersPro> orderspros) {
		this.orderspros = orderspros;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<RefreshToken> getRefreshtokens() {
		return this.refreshtokens;
	}

	public void setRefreshtokens(Set<RefreshToken> refreshtokens) {
		this.refreshtokens = refreshtokens;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<OrdersSer> getOrderssers() {
		return this.orderssers;
	}

	public void setOrderssers(Set<OrdersSer> orderssers) {
		this.orderssers = orderssers;
	}

}
