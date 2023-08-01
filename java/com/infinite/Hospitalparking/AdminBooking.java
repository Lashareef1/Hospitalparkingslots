package com.infinite.Hospitalparking;

import java.time.LocalDateTime;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;




@Entity
@Table(name = "adminbooking")
@ManagedBean(name = "admin")
@SessionScoped
public class AdminBooking {
	
	@Id
	@GenericGenerator(name = "custom_generator", strategy = "com.infinite.Hospitalparking.AidGenerator")
	@GeneratedValue(generator = "custom_generator")
	
	
	
	
	@Column(name = "AdminId")
	private String adminId;
	

	@Column (name="UserId")
	private String userid;
	
	
	
	@Column(name = "slotnumber")
	private String slotNumber;

	
	@Column(name = "vehiclenumber")
	private String VehicleNumber;
	

	@Column(name = "Indate")
	private Date indate;
	
	@Column(name = "Intime")
	private  String intime;
	
	
	@Column(name = "Outtime")
	private String outtime;
	

	@Column(name = "Outdate")
	private Date outdate;
	/*
	@Column(name = "InDateTime")
	private LocalDateTime inDateTime;
	
	@Column(name = "OutDateTime")
    private LocalDateTime outDateTime;
*/
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "vehiclecategory")
	private VehicleCategory vehiclecategory;

	@Column(name = "chargesperhour")
	private int Chargesperhour;
	
	/*@Column(name = "chargesperday")
	private int Chargesperday;

	
	@Column(name = "days")
	private Integer days;*/
	
	@Column(name = "Totalamount")
	private Integer totalamount;
	
	@Column (name="PenaltyAmount")
	private Integer penaltyamount; 
	
	
	  public String getAdminId() {
		return adminId;
	}


	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	
	

	public String getSlotNumber() {
		return slotNumber;
	}


	public void setSlotNumber(String slotNumber) {
		this.slotNumber = slotNumber;
	}



	public String getVehicleNumber() {
		return VehicleNumber;
	}


	public void setVehicleNumber(String vehicleNumber) {
		VehicleNumber = vehicleNumber;
	}










	public VehicleCategory getVehiclecategory() {
		return vehiclecategory;
	}

	public void setVehiclecategory(VehicleCategory vehiclecategory) {
		this.vehiclecategory = vehiclecategory;
	}

	

	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}

	
	
	
	
	

	/*public LocalDateTime getInDateTime() {
		return inDateTime;
	}


	public void setInDateTime(LocalDateTime inDateTime) {
		this.inDateTime = inDateTime;
	}


	public LocalDateTime getOutDateTime() {
		return outDateTime;
	}


	public void setOutDateTime(LocalDateTime outDateTime) {
		this.outDateTime = outDateTime;
	}*/


	public Integer getTotalamount() {
		return totalamount;
	}


	public void setTotalamount(Integer totalamount) {
		this.totalamount = totalamount;
	}


	public int getChargesperhour() {
		return Chargesperhour;
	}


	public void setChargesperhour(int chargesperhour) {
		Chargesperhour = chargesperhour;
	}


	public Date getIndate() {
		return indate;
	}


	public void setIndate(Date indate) {
		this.indate = indate;
	}


	public String getIntime() {
		return intime;
	}


	public void setIntime(String intime) {
		this.intime = intime;
	}


	public String getOuttime() {
		return outtime;
	}


	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}


	public Date getOutdate() {
		return outdate;
	}


	public void setOutdate(Date outdate) {
		this.outdate = outdate;
	}


	public Integer getPenaltyamount() {
		return penaltyamount;
	}


	public void setPenaltyamount(Integer penaltyamount) {
		this.penaltyamount = penaltyamount;
	}


	/*public int getChargesperday() {
		return Chargesperday;
	}


	public void setChargesperday(int chargesperday) {
		Chargesperday = chargesperday;
	}


	public Integer getTotalamount() {
		return totalamount;
	}


	public void setTotalamount(Integer totalamount) {
		this.totalamount = totalamount;
	}


	public Integer getDays() {
		return days;
	}


	public void setDays(Integer days) {
		this.days = days;
	}

*/
	public VehicleCategory[] getVehicleCategoryAv() {
			return VehicleCategory.values();

		
	  }
	

}
