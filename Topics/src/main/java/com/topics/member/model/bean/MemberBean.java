package com.topics.member.model.bean;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "member")
@Setter
@Getter
@Data
@NoArgsConstructor
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

	@Override
	public String toString() {
		return "MemberBean [memberId=" + memberId + ", name=" + name + ", gender=" + gender + ", idno=" + idno
				+ ", email=" + email + ", phone=" + phone + ", birthday=" + birthday + ", account=" + account
				+ ", password=" + password + ", createAccount=" + createAccount + ", isActive=" + isActive + "]";
	}

}
