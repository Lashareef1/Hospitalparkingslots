package com.infinite.Hospitalparking;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "bookings")
@ManagedBean(name = "staff")
@SessionScoped

public class StaffBooking {



	@Id
	@GenericGenerator(name = "custom_generator", strategy = "com.infinite.Hospitalparking.BidGenerator")
	@GeneratedValue(generator = "custom_generator")

	/*@Column (name="UserId")
	private String userid;*/
	
	@Column(name = "bookingId")
	private String BookingId;

	@Column(name = "vehiclenumber")
	private String VehicleNumber;

	@Column(name = "startdate")
//	@Temporal(TemporalType.DATE)
	private Date StartDate;

	@Column(name = "enddate")
//	@Temporal(TemporalType.DATE)
	private Date EndDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "vehiclecategory")
	private VehicleCategory vehiclecategory;

	@Column(name = "slotnumber")
	private String slotNumber;

	@Column(name = "chargesperday")
	private int Chargesperday;

	@Column(name = "Totalamount")
	private Integer totalamount;
	
	
	
/*	@Column(name = "Discontinue")
	private int discontinue;*/
	
	@Column(name = "Renewal")
	private Integer  renewal ;
	
	@Column(name = "RefundAmount")
	private Integer refundAmount;
	
	
	@Column (name="UserId")
	private String userid;
	
	@Column (name="PenaltyAmount")
	private Integer penaltyamount;
	
	@Column(name = "UserName")
	private String userName;
	

	public String getBookingId() {
		return BookingId;
	}

	public void setBookingId(String bookingId) {
		BookingId = bookingId;
	}


	public String getVehicleNumber() {
		return VehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		VehicleNumber = vehicleNumber;
	}

	public Date getStartDate() {
		return StartDate;
	}

	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}

	public Date getEndDate() {
		return EndDate;
	}

	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}

	public VehicleCategory getVehiclecategory() {
		return vehiclecategory;
	}

	public void setVehiclecategory(VehicleCategory vehiclecategory) {
		this.vehiclecategory = vehiclecategory;
	}

	public String getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(String slotNumber) {
		this.slotNumber = slotNumber;
	}

	public int getChargesperday() {
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

	public VehicleCategory[] getVehicleCategoryop() {
		return VehicleCategory.values();

	
	}
	


	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}





	public Integer getRenewal() {
		return renewal;
	}

	public void setRenewal(Integer renewal) {
		this.renewal = renewal;
	}





	public Integer getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Integer refundAmount) {
		this.refundAmount = refundAmount;
	}



	public Integer getPenaltyamount() {
		return penaltyamount;
	}

	public void setPenaltyamount(Integer penaltyamount) {
		this.penaltyamount = penaltyamount;
	}



	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	private Integer days;

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public void setVehiclecategory(String name) {
		// TODO Auto-generated method stub

	}



	

	@Override
	public String toString() {
		return "StaffBooking [BookingId=" + BookingId + ", VehicleNumber=" + VehicleNumber + ", StartDate=" + StartDate
				+ ", EndDate=" + EndDate + ", vehiclecategory=" + vehiclecategory + ", slotNumber=" + slotNumber
				+ ", Chargesperday=" + Chargesperday + ", totalamount=" + totalamount + ", renewal=" + renewal
				+ ", refundAmount=" + refundAmount + ", userid=" + userid + ", days=" + days + "]";
	}

	public int getRemainingDays() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	
	  
	  public boolean isAmbulanceDisabled(String category) {
		    // Your logic here to determine whether to show or hide the AMBULANCE option.
		    // For example:
		    return "AMBULANCE".equals(category); // This will hide the AMBULANCE option.
		}

	  
	  

	
}
