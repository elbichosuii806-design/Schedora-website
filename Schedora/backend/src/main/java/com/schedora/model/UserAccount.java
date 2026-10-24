package com.schedora.model;
import jakarta.persistence.*;
@Entity @Table(name="user_accounts", uniqueConstraints=@UniqueConstraint(columnNames="email"))
public class UserAccount {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Column(nullable=false,unique=true) private String email;
 @Column(nullable=false) private String passwordHash;
 @Column(nullable=false) private String role;
 private String businessName;
 public UserAccount() {}
 public UserAccount(String name,String email,String passwordHash,String role,String businessName){this.name=name;this.email=email;this.passwordHash=passwordHash;this.role=role;this.businessName=businessName;}
 public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;} public String getPasswordHash(){return passwordHash;} public String getRole(){return role;} public String getBusinessName(){return businessName;}
 public void setId(Long v){id=v;} public void setName(String v){name=v;} public void setEmail(String v){email=v;} public void setPasswordHash(String v){passwordHash=v;} public void setRole(String v){role=v;} public void setBusinessName(String v){businessName=v;}
}
