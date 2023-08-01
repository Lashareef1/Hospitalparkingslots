package com.infinite.Hospitalparking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

@ManagedBean(name = "admindao")
@SessionScoped
public class AdminDao {
	 private String category;
	 private String slots;
	 private VehicleCategory vehiclecategory;
	 
	 public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	public String getSlots() {
		return slots;
	}

	public void setSlots(String slots) {
		this.slots = slots;
	}

	public VehicleCategory getVehiclecategory() {
		return vehiclecategory;
	}

	public void setVehiclecategory(VehicleCategory vehiclecategory) {
		this.vehiclecategory = vehiclecategory;
	}

	/*
	This method is use for Admin is Booking for visitors,
	here we are booking slots for 30 days ,Admin can booked Upto 20 slots for visitors,
	here first he is booking  a slot after while exiting  only the bill will generate based on end date.*/
	
	public String Addadmin(AdminBooking adminBooking,String userid) {
	    try {
	        System.out.println("in the method");
	        System.out.println(adminBooking.getVehicleNumber());
	        System.out.println(adminBooking.getSlotNumber());
	        System.out.println(adminBooking.getIndate());
	        System.out.println(adminBooking.getIntime());
	        
	      adminBooking.setUserid(userid);
	        System.out.println(userid);
	        
	       adminBooking.setChargesperhour(0);
	       adminBooking.setPenaltyamount(0);
	        

	        Session session =ConnectionObject.getSession();
	        Transaction t = session.beginTransaction();
	        session.save(adminBooking);
	        t.commit();
	        session.clear();
	        
	        System.out.println("Record Inserted..!!");
	        
    	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Slot booked successfully", null));

	        return null;
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        
    	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Slot bookingfailed", null));

	        return null;
	    }
	}


	
	
	/*This method is use for Admin is  calculating Exit time for visitors, which date is exiting based on that total amount will be calculate,
	here penalty also there if visitore park in wrong place admin can give penalty amount at the time based on penalty amount the total amount will be calculate.
	*/
	
	public String calculateExitTime(AdminBooking adminBooking) {
		System.out.println( "penalty:"+ adminBooking.getPenaltyamount());
	    try {
	        // Get the calculated duration
	        long durationInMillis = calculateDuration(adminBooking);
	        System.out.println(durationInMillis);

	        // Convert the duration to hours 
	        long durationInHours = durationInMillis / (60 * 60 * 1000);
	        System.out.println(durationInHours);

	        VehicleCategory category = adminBooking.getVehiclecategory();
	        adminBooking.setChargesperhour(category.getCost());
	        adminBooking.setPenaltyamount(adminBooking.getPenaltyamount());;

	        // Get the charges per hour from the category
	        int chargesPerHour = category.getCost();
	        int penatlys = adminBooking.getPenaltyamount();
	        System.out.println(penatlys);

	        // Calculate the total charges based on durationInHours and chargesPerHour
	        int totalAmount = (int) (durationInHours * chargesPerHour) + penatlys;
	        System.out.println(totalAmount);

	        adminBooking.setTotalamount(totalAmount);
	        
//	        Session session = ConnectionObject.getSession();
	        SessionFactory sf = SessionHelper.getConnection();
	        Session session = sf.openSession();
	        Transaction transaction = session.beginTransaction();
	        session.update(adminBooking);
	        transaction.commit();
	        session.close();
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exit successfully! and Totalamount calculated", null));
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Total Amount: " + adminBooking.getTotalamount(), null));

	        return null;
	    } catch (Exception e) {
	        e.printStackTrace();
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Unable to calculate and store charges!", null));
	    }
		return null;
	}


	private long calculateDuration(AdminBooking adminBooking) throws ParseException {
	        Date inDateTime = combineDateAndTime(adminBooking.getIndate(), adminBooking.getIntime());
	        Date outDateTime = combineDateAndTime(adminBooking.getOutdate(), adminBooking.getOuttime());

	        if (inDateTime == null || outDateTime == null) {
	            return 0;
	        }

	        // Calculate the duration in milliseconds
	        return outDateTime.getTime() - inDateTime.getTime();
	    }

	    private Date combineDateAndTime(Date date, String time) throws ParseException {
	        if (date == null || time == null || time.isEmpty()) {
	            return null;
	        }

	        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(date);
	        String dateTimeString = dateString + " " + time;
	        return dateTimeFormat.parse(dateTimeString);
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	/* This method is use for Searching the category  which is stored in DB*/


	public List<AdminBooking> searchByCategory(Category category, VehicleCategory vehicleCategory ) {
	    Session session = ConnectionObject.getSession();
	    Criteria cr = session.createCriteria(AdminBooking.class);
		cr.add(Restrictions.eq("category", category));
		cr.add(Restrictions.eq("VehicleCategory", vehicleCategory));
		
	    List<AdminBooking> bookings = cr.list();
//	    session.close();

	    return bookings;
	}
	
	public List<AdminBooking> showstaffdtls() {
		SessionFactory sf = SessionHelper.getConnection();
        Session session = sf.openSession();
//		Session session = ConnectionObject.getSession();
		Criteria cr = session.createCriteria(AdminBooking.class);
		
		List<AdminBooking> list = cr.list();
		return list;

	}
	
/*	In this method we are showing Two WHEELER details from both end Admin and Staff.*/
	
	public List<AdminBooking> showTWOWHEELERdetails() {
	    Session session = ConnectionObject.getSession();
	    Criteria cr = session.createCriteria(AdminBooking.class);
	    Criteria cr1 = session .createCriteria(StaffBooking.class);
	    cr.add(Restrictions.eq("vehiclecategory", vehiclecategory.TWOWHEELER)); // Use "vehiclecategory" instead of "VehicleCategory"
	    
	    cr1.add(Restrictions.eq("vehiclecategory", vehiclecategory.TWOWHEELER)); // Use "vehiclecategory" instead of "VehicleCategory"

	    List<AdminBooking> list = cr.list();
	    List<AdminBooking> list1 = cr1.list();
	    session.close();
	   
	  

        // Combine the results from both lists into a single list
        List<AdminBooking> combinedList = new ArrayList<>();
        combinedList.addAll(list);
        combinedList.addAll(list1);

        return combinedList;
    }
	
	
	/*	In this method we are showing AMBULANCE details from both end Admin and Staff.*/

	
	public List<AdminBooking> showAMBULANCEdetails() {
		Session session = ConnectionObject.getSession();
		Criteria cr = session.createCriteria(AdminBooking.class);
		Criteria cr1 = session .createCriteria(StaffBooking.class);
		cr.add(Restrictions.eq("vehiclecategory", vehiclecategory.AMBULANCE)); // Use "vehiclecategory" instead of "VehicleCategory"
		
		cr1.add(Restrictions.eq("vehiclecategory", vehiclecategory.AMBULANCE)); // Use "vehiclecategory" instead of "VehicleCategory"
		
		List<AdminBooking> list = cr.list();
		List<AdminBooking> list1 = cr1.list();
		session.close();
		
		
		
		// Combine the results from both lists into a single list
		List<AdminBooking> combinedList = new ArrayList<>();
		combinedList.addAll(list);
		combinedList.addAll(list1);
		
		return combinedList;
	}
	
	/*	In this method we are showing FOURWHEELER details from both end Admin and Staff.*/

	
	public List<AdminBooking> showFOURWHEELERdetails() {
		Session session = ConnectionObject.getSession();
		Criteria cr = session.createCriteria(AdminBooking.class);
		Criteria cr1 = session .createCriteria(StaffBooking.class);
		cr.add(Restrictions.eq("vehiclecategory", vehiclecategory.FOURWHEELER)); // Use "vehiclecategory" instead of "VehicleCategory"
		
		cr1.add(Restrictions.eq("vehiclecategory", vehiclecategory.FOURWHEELER)); // Use "vehiclecategory" instead of "VehicleCategory"
		
		List<AdminBooking> list = cr.list();
		List<AdminBooking> list1 = cr1.list();
//		session.close();
		
		// Combine the results from both lists into a single list
		List<AdminBooking> combinedList = new ArrayList<>();
		combinedList.addAll(list);
		combinedList.addAll(list1);
		
		return combinedList;
	}
	
	
	
	
	
	
	
	
	
	
	    private String selectedCategory;

	    public String getSelectedCategory() {
	        return selectedCategory;
	    }

	    public void setSelectedCategory(String selectedCategory) {
	        this.selectedCategory = selectedCategory;
	    }

	    public String search() {
	        // You can perform additional logic here, such as setting up search criteria based on the selected category
	        return "Staffsearchdetails.xhtml?faces-redirect=true";
	    }
	
	
	private String selectedSlot;

	  public String getSelectedSlot() {
	    return selectedSlot;
	  }

	  public String setSelectedSlot(String selectedSlot) {
	    this.selectedSlot = selectedSlot;
	    return redirectToAdminDetailsPage();
	  }

	  public String redirectToAdminDetailsPage() {
	    return "AdminDetails.xhtml?faces-redirect=true&amp;slotNumber=" + selectedSlot;
	  }

	  
	  
	  
	  /*In this method  we are searching Slots booked  or NOT  from both side Admin and Staff*/
	  
	  
	  
	  public boolean isSlotBooked(String slotNumber) {

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
		        session.close();
		    }
		}


	  
	  public String selectedCategory(String category) {
	      this.category=category;
	        System.out.println("Selected category: " + category);
	        
	        return null;
	    }
	  public String selectedSlots(String slots) {
		  this.slots=slots;
		  System.out.println("Selected slots: " + slots);
		  
		  return null;
	  }
}