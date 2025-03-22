package com.topics.appointment.model.bean;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "owner") 
public class Owner {

    @Id
    @Column(name = "MEMBER_ID")  
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int memberId;

    @Column(name = "MEMBER_NAME", nullable = false, length = 50)  
    private String memberName;

    @Column(name = "PHONE_NUMBER", nullable = false, length = 15)  
    private String phoneNumber;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)  
    private String email;

    @Column(name = "ADDR", nullable = false, length = 255)  
    private String address;

    @Column(name = "JOIN_DATE", nullable = false)  
    @Temporal(TemporalType.DATE)  
    private Date joinDate;
}