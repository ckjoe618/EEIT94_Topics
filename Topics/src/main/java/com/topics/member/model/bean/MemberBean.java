package com.topics.member.model.bean;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity @Table(name = "member")
//@Setter 
//@Getter 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberBean {
	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int memberId;
	
	@Column(name = "member_name")
	private String name;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "idno")
	private String idno;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "birthday_date")
	private LocalDate birthday;
	
	@Column(name = "account")
	private String account;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "create_account_date")
	private LocalDateTime createAccount = LocalDateTime.now();
	
	@Column(name = "active_status")
	private boolean isActive = true;

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getCreateAccount() {
		return createAccount;
	}

	public void setCreateAccount(LocalDateTime createAccount) {
		this.createAccount = createAccount;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "MemberBean [memberId=" + memberId + ", name=" + name + ", gender=" + gender + ", idno=" + idno
				+ ", email=" + email + ", phone=" + phone + ", birthday=" + birthday + ", account=" + account
				+ ", password=" + password + ", createAccount=" + createAccount + ", isActive=" + isActive + "]";
	}

}
