package com.infinite.Hospitalparking;



import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


@ManagedBean(name = "staffDao")
@SessionScoped
public class StaffDao {
    
	
	
	

	


	 /*
	 This method is use for Booking Slot, staff want to book a TWO WHEELER or FOUR WHEELER slot he can book  upto 30 days ,
	 only 1 slot  can book one staff member in that 30 days , if he discontinued in middle or he is booking for only 5 days , 
	 after again he can book one more slot .here we are calculating how many days he booked , 
	 that many days we are charging based on Category(TWO WHEELER or FOUR WHEELER).
	 
	 */
	
	
	public String bookSlot(StaffBooking staffBooking,String userid,String userName, Integer remainingAmount, Integer renewal) {
		System.out.println(" Slot Number:"+ staffBooking.getSlotNumber());
		String s1 = null;
	    try {
			System.out.println(staffBooking.getSlotNumber());
            System.out.println("Vehicle Number: " + staffBooking.getVehicleNumber());
	     
//            staffBooking.setRefundAmount(remainingAmount != null ? remainingAmount : 0);
            	staffBooking.setRefundAmount(0);
            System.out.println("Remaining Amount: " + staffBooking.getRefundAmount());
            staffBooking.setRenewal(renewal != null ? renewal : 0);
            System.out.println("Renewal: " + staffBooking.getRenewal());

	        staffBooking.setUserid(userid);
	        System.out.println(userid);
	        
	        staffBooking.setUserName(userName);
	        System.out.println(userName);
	        
//            SessionFactory sf = SessionHelper.getConnection();
    	    Session session = ConnectionObject.getSession();
            System.out.println("Vehicle Number: " + staffBooking.getVehicleNumber());
            
            
            List<StaffBooking> userBookings = showstaffdetails(userid);
            if (!userBookings.isEmpty()) {
                // User has already booked a slot
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "You have already booked a slot.", null));
                return null; // Or redirect to an error page
            }

		    org.hibernate.Query cr = session.createQuery("select slotNumber from StaffBooking");
		    
		    List<StaffBooking> bookings = cr.list();
		    String s = bookings.toString();

	        System.out.println("VehicleCategory: " + staffBooking.getVehicleCategoryop());

		    if(s.contains(staffBooking.getSlotNumber())){
		    	
		   	 FacesContext context = FacesContext.getCurrentInstance();
				       
		    }else{
		    	VehicleCategory category = staffBooking.getVehiclecategory();

		        // Set the vehicle category and associated cost
		        staffBooking.setVehiclecategory(category);
		        staffBooking.setChargesperday(category.getCost());

		        // Calculate the number of days
		        Integer days = calculateDays(staffBooking.getStartDate(), staffBooking.getEndDate());
		        staffBooking.setDays(days);

		        // Calculate the total charges
		        Integer totalAmount = days * category.getCost();
		        
		        staffBooking.setTotalamount(totalAmount);
		        
		      
		    	 Transaction t = session.beginTransaction();
			        session.save(staffBooking);
			        t.commit();
			        session.clear();
		    	s1="Submit.xhtml?faces-redirect:true";
		    	 // Display a success message
	    	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Slot booked successfully", null));

		    }
//	        List<StaffBooking> existingBookings = searchBySlotNumber(staffBooking.getSlotNumber());
//	        System.out.println(existingBookings);
//	        
//	        
//	        if (existingBookings.contains(staffBooking.getSlotNumber())) {
//	            return "Slot not available";
//	        }

	        System.out.println("In the method");
	        System.out.println("Vehicle Number: " + staffBooking.getVehicleNumber());
	        System.out.println("StartDate: " + staffBooking.getStartDate());
	        System.out.println("EndDate: " + staffBooking.getEndDate());
	        System.out.println("VehicleCategory: " + staffBooking.getVehicleCategoryop());
	        System.out.println("Number of days:" + staffBooking.getDays());

	        
	        // Save the staff booking
	    //    SessionFactory sf1 = SessionHelper.getConnection();
//	        Session session1 = ConnectionObject.getSession();
	        SessionFactory sf = SessionHelper.getConnection();
	        Session session1 = sf.openSession();
//	        System.out.println("Record Inserted..!!");

	        
	    
		    } catch (Exception e) {
	        // Handle the exception and provide an appropriate error message
	        e.printStackTrace();  
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "An error occurred while booking the slot", null));

		    }
	    return s1;
	}


	public Integer calculateDays(Date checkinDate, Date checkoutDate) {
	    if (checkinDate == null || checkoutDate == null) {
	        // Handle null dates gracefully
	        return 0;
	    }
	    long Date = checkoutDate.getTime() - checkinDate.getTime();
	    long InDays = TimeUnit.MILLISECONDS.toDays(Date);
	    return (int) InDays + 1;
}

	
	public List<StaffBooking> searchBySlotNumber(String slotnumber,String userid) {
	   
	    Session session = ConnectionObject.getSession();
	    Criteria cr = session.createCriteria(StaffBooking.class);
	    cr.add(Restrictions.eq("slotNumber", slotnumber));
	    cr.add(Restrictions.eq("userid", userid));
	    List<StaffBooking> bookings = cr.list();
	    session.close();

	    return bookings;
	}
	
	
	public List<StaffBooking> showstaffdetails(String userid) {

		SessionFactory sf = SessionHelper.getConnection();
        Session session = sf.openSession();
		Criteria cr = session.createCriteria(StaffBooking.class);
		cr.add(Restrictions.eq("userid", userid));
		List<StaffBooking> list = cr.list();
		return list;

	}
	

	
	public List<StaffBooking> showstaffdtls() {

		
		Session session = ConnectionObject.getSession();
		Criteria cr = session.createCriteria(StaffBooking.class);
		List<StaffBooking> list = cr.list();
		return list;

	}



	/*This method is use  for Calculating remaining amount ,
	if we discontinued after booking its will calculate  remaing amount and refund it.
	example if  staff booked 10 days  20/07/2023 to 30/07/2023, 
	he want to discontinue  after 5 days which is 25/07/2023,
	so remaining 5 days amount will be refund
	*/

	
	public void calculateRemainingAmount(StaffBooking staffBooking) {
		
		System.out.println(staffBooking);
		  try {
		
		Date checkinDate = staffBooking.getStartDate();
	    System.out.println(staffBooking.getStartDate());

	    
	    Date checkoutDate = staffBooking.getEndDate();
	    System.out.println(staffBooking.getEndDate());

	    int totalAmount = staffBooking.getTotalamount();
	    System.out.println(staffBooking.getTotalamount());

	    if (checkinDate == null || checkoutDate == null) {
	        // Handle the case when the dates are not set
	        System.out.println("Check-in or check-out date is null.");
	        return;
	    }

	    if (totalAmount <= 0) {
	        // Handle the case when the total amount is not valid
	        System.out.println("Invalid total amount.");
	        return;
	    }

	    int totalDays = calculateDays(checkinDate, checkoutDate);
	    int discontinuedDays = calculateDiscontinuedDays(checkinDate);
	    int remainingDays = totalDays - discontinuedDays;

	    if (totalDays <= 0) {
	        // Handle the case when the total days is not valid
	        System.out.println("Invalid total days.");
	        return;
	    }

	    if (remainingDays <= 0) {
	        // Handle the case when there are no remaining days
	        System.out.println("No remaining days.");
	        return;
	    }

	    int chargePerDay = totalAmount / totalDays;
	    Integer refundAmount = remainingDays * chargePerDay;
	    staffBooking.setRefundAmount(refundAmount);;
	    System.out.println("staffBookingremaingamount:"+ staffBooking.getRefundAmount());
	    
	    
	  //  staffBooking.setSlotNumber(null);
	    
	 // Update the staffBooking object in the database
	    SessionFactory sf = SessionHelper.getConnection();
        Session session = sf.openSession();
//        Session session = ConnectionObject.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(staffBooking);
        transaction.commit();
         session.close();

        // Display the refund amount in the UI
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Refund Amount: " + staffBooking.getRefundAmount(), null));

        // Delete the staffBooking record
       deleteStaffBooking(staffBooking);
        
	}
catch (NullPointerException e) {
    // Handle the exception and provide an appropriate error message
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
}

	}	
	
	
	
	
	

	public int calculateDiscontinuedDays(Date checkoutDate) {
	    if (checkoutDate == null) {
	        // Handle the case when the checkout date is not set
	        System.out.println("Checkout date is null.");
	        return 0;
	    }

	    LocalDate currentDate = LocalDate.now();
	    LocalDate endDate = convertToLocalDate(checkoutDate);

	    long discontinuedDays = ChronoUnit.DAYS.between(currentDate, endDate);
	    return Math.max(0, (int) discontinuedDays);
	}

	public LocalDate convertToLocalDate(Date dateToConvert) {
		System.out.println(dateToConvert);
	    if (dateToConvert == null) {
	        // Handle the case when the date to convert is null
	        System.out.println("Date to convert is null.");
	        return null;
	    }
	    System.out.println(dateToConvert.toInstant());
	    System.out.println(dateToConvert.toInstant().atZone(ZoneId.systemDefault()));
	    System.out.println(dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

	    return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}


	

    public void deleteStaffBooking(StaffBooking staffBooking) {
        try {
            // Delete the staffBooking record from the database
//            SessionFactory sf = SessionHelper.getConnection();
//            Session session = ConnectionObject.getSession();
        	  SessionFactory sf = SessionHelper.getConnection();
              Session session = sf.openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(staffBooking);
            transaction.commit();
            session.close();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Staff booking record deleted successfully", null));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred while deleting the staff booking record", null));
        }
    }

	
	
	
	/*This method is use for RENEWAL booking,it will take end date which is stored in DB and current date default system date
	 * it will check here the staff slot is expired are not ,is it expired it will renewal.
	*/
	
	
	public void renewBooking(StaffBooking bookingId) {
	System.out.println("Booking ID: " + bookingId);
	    StaffBooking staffBooking = fetchStaffBooking(bookingId);
	    System.out.println("StaffBooking: " + staffBooking);
	    if (staffBooking == null) {
	        // Handle the case when the booking does not exist
	        System.out.println("Booking not found.");
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Booking not found.", null));

	        return;
	    }
	    
	    Date endDate = staffBooking.getEndDate();
	    System.out.println("enddate:"+endDate);
	    LocalDate currentDate = LocalDate.now();
	    System.out.println("currentdate:"+currentDate);
	    LocalDate expiryDate = convertToLocalDate(endDate);
	    System.out.println("expiredate"+expiryDate);
	    
	    if (currentDate.isBefore(expiryDate)) {
	        // Handle the case when the booking has not yet expired
	        System.out.println("Booking has not expired yet.");
		    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Booking has not expired yet.", null));

	        return;
	    }
	    
	    // Renew the booking by updating the end date and remaining amount
	    LocalDate renewedEndDate = expiryDate.plusDays(30); // Renew for 30 days
	   // LocalDate currentDate = LocalDate.now();
	    System.out.println("renewedDate"+renewedEndDate);
	    
	    Date newEndDate = convertToDate(renewedEndDate);
	    System.out.println("newDATE:"+newEndDate);
	    Date currentDate1 = convertToDate(currentDate);
	    System.out.println("current1:"+ currentDate1);
	    staffBooking.setStartDate(currentDate1);
	    staffBooking.setEndDate(newEndDate);
	    
	    //staffBooking.setSlotNumber(staffBooking.getSlotNumber());
	    //System.out.println(staffBooking.getSlotNumber());
	    calculateRenewalAmount(staffBooking);
	    
	    SessionFactory sf = SessionHelper.getConnection();
	      Session session = sf.openSession();	   
//	      Session session = ConnectionObject.getSession();
	    Transaction transaction = session.beginTransaction();
	    transaction.commit();
	    session.update(staffBooking);
	    session.close();
	    
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Booking Renewed!", null));
	}

	public Date convertToDate(LocalDate localDate) {
	    if (localDate == null) {
	        System.out.println("LocalDate is null.");
	        return null;
	    }

	    Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
	    return Date.from(instant);
	}

	
	public StaffBooking fetchStaffBooking(StaffBooking bookingId) {
		System.out.println("Booking ID: " + bookingId);
//	    Session session = ConnectionObject.getSession();
		 SessionFactory sf = SessionHelper.getConnection();
	      Session session = sf.openSession();
	    try {
	    	
	    	 System.out.println("Fetching StaffBooking for Booking ID: " + bookingId);
	        // StaffBooking staffBooking = (StaffBooking) session.get(StaffBooking.class, bookingId);
	    	 System.out.println( bookingId.getBookingId());
	    	// StaffBooking staffBooking = new StaffBooking();
	    	// bookingId= staffBooking;
	         System.out.println("Fetched StaffBooking: " + bookingId);
	         return bookingId;
	    } catch (HibernateException e) {
	        // Handle the exception appropriately
	        e.printStackTrace();
	        return null;
	    } finally {
	        session.close();
	    }
	}
	/*This method is use for calculating  RENEWAL amount , 
	 * in this method we are calculating no.of days ,
	 *  and multiplying with category what ever selected ,
	 * After that it will set to be Total amount
	 * */
	
	public void calculateRenewalAmount(StaffBooking staffBooking) {
		  try {
		
	
    	VehicleCategory category = staffBooking.getVehiclecategory();

        // Set the vehicle category and associated cost
        staffBooking.setVehiclecategory(category);
        staffBooking.setChargesperday(category.getCost());

        // Calculate the number of days
        Integer days = calculateDays(staffBooking.getStartDate(), staffBooking.getEndDate());
        staffBooking.setDays(days);

        // Calculate the total charges
        Integer totalAmount = days * category.getCost();
        System.out.println(totalAmount);
        staffBooking.setRenewal(totalAmount);
       // staffBooking.setSlotNumber(staffBooking.getSlotNumber());
       // System.out.println(staffBooking.getSlotNumber());
	    
	 // Update the staffBooking object in the database
      SessionFactory sf = SessionHelper.getConnection();
      Session session = sf.openSession();
//      Session session = ConnectionObject.getSession();
      Transaction transaction = session.beginTransaction();
      session.update(staffBooking);
      transaction.commit();
      session.close();

      // Display the refund amount in the UI
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Renewal Amount: " + staffBooking.getRenewal(), null));

     
	}
catch (NullPointerException e) {
  // Handle the exception and provide an appropriate error message
  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
}

	}	
	
	
	
	/*This  method is use for renewal disabling button,
	 *  if that staff slot is not expired its will be disable until expire date,
	 *  after that it will be enable.
	*/
	
	
	public boolean isExpired(Date endDate) {
	    LocalDate currentDate = LocalDate.now();
	    LocalDate expiryDate = convertToLocalDate(endDate);
	    LocalDate oneDaysBeforeExpiry = expiryDate.minusDays(1);
	    
	    return currentDate.isAfter(oneDaysBeforeExpiry);
	
	}

	
	
	
	
	
	
	private String selectedSlot;

	  public String getSelectedSlot() {
	    return selectedSlot;
	  }

	  public String setSelectedSlot(String selectedSlot) {
	    this.selectedSlot = selectedSlot;
	    return redirectToStaffPage();
	  }

	  public String redirectToStaffPage() {
	    return "Staff.xhtml?faces-redirect=true&amp;slotNumber=" + selectedSlot;
	  }

	  
	  public boolean isSlotBooked(String slotNumber) {
//		    SessionFactory sf = SessionHelper.getConnection();
		    Session session = ConnectionObject.getSession();
		    try {
		        org.hibernate.Query staffQuery = session.createQuery("SELECT COUNT(*) FROM StaffBooking WHERE slotNumber = :slot");
		        staffQuery.setParameter("slot", slotNumber);
		        long staffCount = (long) staffQuery.uniqueResult();
		        
		        org.hibernate.Query adminQuery = session.createQuery("SELECT COUNT(*) FROM AdminBooking WHERE slotNumber = :slot");
		        adminQuery.setParameter("slot", slotNumber);
		        long adminCount = (long) adminQuery.uniqueResult();

		        return staffCount > 0 || adminCount > 0;
		    } finally {
//		        session.close();
		    }
		}



	
	

	
	  public void calculatePenaltyAmount(StaffBooking staffBooking) {
		    try {
		        Date lastDate = staffBooking.getEndDate();  // Retrieve the last date from the staff booking object
		        Date currentDate = new Date();  // Get the current date

		        int chargePerDay = staffBooking.getTotalamount() / calculateDays(staffBooking.getStartDate(), staffBooking.getEndDate());

		        long overstayedDays = (currentDate.getTime() - lastDate.getTime()) / (1000 * 60 * 60 * 24);

		        int penaltyamount = (int) (overstayedDays * chargePerDay);

		        staffBooking.setPenaltyamount(penaltyamount);;

		        SessionFactory sf = SessionHelper.getConnection();
		        Session session = ConnectionObject.getSession();
		        Transaction transaction = session.beginTransaction();
		        session.update(staffBooking);
		        transaction.commit();
		        session.close();

		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Penalty Amount Calculated!", null));
		    } catch (NullPointerException e) {
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		    }
		}

	

	
   
		


 
	

//	public static void main(String[] args) {
//		String s1 = null;
//		String slotnumber ="G2";
//		SessionFactory sf = SessionHelper.getConnection();
//	    Session session = ConnectionObject.getSession();
//	    org.hibernate.Query cr = session.createQuery("select slotNumber from StaffBooking");
//	    List<StaffBooking> bookings = cr.list();
//	    String s = bookings.toString();
//	    if(s.contains("G9")){
//	    	System.out.println("Its already booked");
//	    }else{
//	    	s1 = "Submit";
//	    }
//	    return s1;
//	   
//	
//	}


	}
