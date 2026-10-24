package com.schedora.model;
import jakarta.persistence.*;
@Entity @Table(name="services")
public class ServiceListing {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name; @Column(nullable=false) private String category; @Column(nullable=false) private String business; @Column(nullable=false) private String location; @Column(nullable=false) private Integer price; @Column(nullable=false) private String duration;
 @ManyToOne(fetch=FetchType.EAGER,optional=false) @JoinColumn(name="owner_id",nullable=false) private UserAccount owner;
 public ServiceListing(){} public ServiceListing(String name,String category,String business,String location,Integer price,String duration,UserAccount owner){this.name=name;this.category=category;this.business=business;this.location=location;this.price=price;this.duration=duration;this.owner=owner;}
 public Long getId(){return id;} public String getName(){return name;} public String getCategory(){return category;} public String getBusiness(){return business;} public String getLocation(){return location;} public Integer getPrice(){return price;} public String getDuration(){return duration;} public UserAccount getOwner(){return owner;}
 public void setName(String v){name=v;} public void setCategory(String v){category=v;} public void setBusiness(String v){business=v;} public void setLocation(String v){location=v;} public void setPrice(Integer v){price=v;} public void setDuration(String v){duration=v;} public void setOwner(UserAccount v){owner=v;}
}
