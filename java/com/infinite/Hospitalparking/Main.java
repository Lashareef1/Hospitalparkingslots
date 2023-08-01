package com.infinite.Hospitalparking;

public class Main {
	
	

private static StaffBooking staffBooking;

public static void main(String[] args) {
	 
	 
	/* SignupDaoImpl sigg = new SignupDaoImpl();
		
     sigg.resetpas("malar1", "Shareef@123", "L@sha123");
//     System.out.println("jbjhb");*/
//	StaffDao staff = new StaffDao();
//	staff.calculateRemainingAmount(staffBooking);
//	  System.out.println(staffBooking.getRefundAmount());
//	  System.out.println( staffBooking.getTotalamount());

	SignupDaoImpl sig = new SignupDaoImpl();
	sig.searchBySignupUserName("bharath", "Shareef@123", Category.ADMIN);
	
	  System.out.println(sig.getUserName());
	  System.out.println("hggh");
	  System.out.println();
}

	  
}
	

