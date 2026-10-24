package com.schedora.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="bookings", indexes=@Index(columnList="appointment_date,appointment_time"))
public class Booking {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.EAGER) @JoinColumn(name="service_id") private ServiceListing service;
 @ManyToOne(optional=false,fetch=FetchType.EAGER) @JoinColumn(name="customer_id") private UserAccount customer;
 @Column(nullable=false) private String appointmentDate; @Column(nullable=false) private String appointmentTime; @Column(nullable=false) private String status="Pending"; private String notes; @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
 public Booking(){} public Booking(ServiceListing s,UserAccount c,String d,String t,String n){service=s;customer=c;appointmentDate=d;appointmentTime=t;notes=n;status="Pending";createdAt=LocalDateTime.now();}
 public Long getId(){return id;} public ServiceListing getService(){return service;} public UserAccount getCustomer(){return customer;} public String getAppointmentDate(){return appointmentDate;} public String getAppointmentTime(){return appointmentTime;} public String getStatus(){return status;} public String getNotes(){return notes;} public LocalDateTime getCreatedAt(){return createdAt;} public void setStatus(String v){status=v;}
}
