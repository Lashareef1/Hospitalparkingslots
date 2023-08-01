package com.infinite.Hospitalparking;

import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.component.password.Password;


@ManagedBean(name = "SignupDao")
@SessionScoped

public class SignupDaoImpl {

	private SignupUser sig;
 
	public SignupUser getSig() {
		return sig;
	}

	public void setSig(SignupUser sig) {
		this.sig = sig;
	}
	
	
	private String userName;
	private String oldpassword;
	private String newpassword;
	
	private String securityquestions1;
	
	private String securityAnswer1;
	
	private String securityquestions2;
	
	private String securityAnswer2;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	
	

	public String getSecurityquestions1() {
		return securityquestions1;
	}

	public void setSecurityquestions1(String securityquestions1) {
		this.securityquestions1 = securityquestions1;
	}

	public String getSecurityAnswer1() {
		return securityAnswer1;
	}

	public void setSecurityAnswer1(String securityAnswer1) {
		this.securityAnswer1 = securityAnswer1;
	}

	public String getSecurityquestions2() {
		return securityquestions2;
	}

	public void setSecurityquestions2(String securityquestions2) {
		this.securityquestions2 = securityquestions2;
	}

	public String getSecurityAnswer2() {
		return securityAnswer2;
	}

	public void setSecurityAnswer2(String securityAnswer2) {
		this.securityAnswer2 = securityAnswer2;
	}
	public String adduser(SignupUser signupUser) {
	    System.out.println("In the Add Method");

	    // Check if the selected security questions are the same
	    System.out.println(signupUser.getSecurityquestions1());
	    System.out.println(signupUser.getSecurityquestions2());
	    if (signupUser.getSecurityquestions1() != null && signupUser.getSecurityquestions1().equals(signupUser.getSecurityquestions2())) {
	        FacesContext context = FacesContext.getCurrentInstance();
	        if (context != null) {
	            FacesMessage message = new FacesMessage("Security questions must be different.");
	            message.setSeverity(FacesMessage.SEVERITY_ERROR);
	            context.addMessage("signupForm:securityQuestion2", message);
	            return null; // Return null to stay on the same page and display the error message
	        }
	    }

	    SessionFactory sf = SessionHelper.getConnection();
	    Session session = sf.openSession();
	    Transaction t = session.beginTransaction();
	    session.save(signupUser);
	    t.commit();
	    session.clear();
	    System.out.println("Record Inserted..!!");
	    return "LoginPage.xhtml?faces-redirect=true";
	}


	
	
	
	
	
	
	public void checkDuplicateQuestion(String securityQuestion1, String securityQuestion2) {
	    if (securityQuestion1 != null && securityQuestion2 != null && securityQuestion1.equals(securityQuestion2)) {
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage("securityQuestion2", new FacesMessage("Please select a different security question."));
	        context.renderResponse();
	    }
	}
	

	public String validateMe(SignupUser signupUser) {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(SignupUser.class);
		System.out.println(signupUser.getUserName());
		System.out.println(signupUser.getCategoryop());
		cr.add(Restrictions.eq("userName", signupUser.getUserName()));
		cr.add(Restrictions.eq("password", signupUser.getPassword()));
		cr.add(Restrictions.eq("category",signupUser.getCategory()));
		cr.setProjection(Projections.rowCount());
		long count = (Long) cr.uniqueResult();
		if (count == 1) {
			SignupUser p = searchBySignupUserName(signupUser.getUserName(), signupUser.getPassword(),signupUser.getCategory());

	        if (Category.STAFF.equals(p.getCategory())) {
	            sessionMap.put("UserInfo", p);
	            sig = p;
	            System.out.println("Staff Login Success");
	            return "ParkingMenu.xhtml?faces-redirect=true";
	        } else if (Category.ADMIN.equals(p.getCategory())) {
	            sessionMap.put("UserInfo", p);
	            sig = p;
	            System.out.println("Admin Login Success");
	            return "AdminMenu.xhtml?faces-redirect=true";
	        } else {
	            System.out.println("Invalid Category");
	        }
	       
		} else {
			System.out.println("Login Failed");
			
    	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid username or password or select correct category.", null));

			return null;
		}
		return null;
		
	}
	
	

	public SignupUser searchBySignupUserName(String username, String password ,Category category ) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(SignupUser.class);
		cr.add(Restrictions.eq("userName", username));
		cr.add(Restrictions.eq("password", password));
		cr.add(Restrictions.eq("category", category));

		SignupUser signupUser = (SignupUser) cr.uniqueResult();
		return signupUser;
	}

	
/*	public String adduser(SignupUser signupUser) {
	    System.out.println("In the Add Method");

	    // Check if the selected security questions are the same
	    System.out.println(signupUser.getSecurityquestions1());
	    System.out.println(signupUser.getSecurityquestions2());
	    if (signupUser.getSecurityquestions1() != null && signupUser.getSecurityquestions1().equals(signupUser.getSecurityquestions2())) {
	        FacesContext context = FacesContext.getCurrentInstance();
	        if (context != null) {
	            FacesMessage message = new FacesMessage("Security questions must be different.");
	            message.setSeverity(FacesMessage.SEVERITY_ERROR);
	            context.addMessage("signupForm:securityQuestion2", message);
	            return null; // Return null to stay on the same page and display the error message
	        }
	    }

	    SessionFactory sf = SessionHelper.getConnection();
	    Session session = sf.openSession();
//	    Session session = ConnectionObject.getSession();
		String pwd = EntryptPassword.getCode(signupUser.getPassword());
		signupUser.setPassword(pwd);
	    Transaction t = session.beginTransaction();
	    session.save(signupUser);
	    t.commit();
	    session.clear();
	    System.out.println("Record Inserted..!!");
	    return "LoginPage.xhtml?faces-redirect=true";
	}


	
	
	
	
	
	
	public void checkDuplicateQuestion(String securityQuestion1, String securityQuestion2) {
	    if (securityQuestion1 != null && securityQuestion2 != null && securityQuestion1.equals(securityQuestion2)) {
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage("securityQuestion2", new FacesMessage("Please select a different security question."));
	        context.renderResponse();
	    }
	}
	

	public String validateMe(SignupUser signupUser) {
		String encr = EntryptPassword.getCode(signupUser.getPassword());

		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
//		Session session = ConnectionObject.getSession();
		Criteria cr = session.createCriteria(SignupUser.class);
		//System.out.println(signupUser.getUserName());
	//	System.out.println(signupUser.getCategoryop());
		cr.add(Restrictions.eq("userName", signupUser.getUserName()));
		cr.add(Restrictions.eq("password", encr.trim()));
		cr.add(Restrictions.eq("category",signupUser.getCategory()));
		cr.setProjection(Projections.rowCount());
		System.out.println(signupUser.getUserName());
		System.out.println(signupUser.getPassword());
		System.out.println(signupUser.getCategory());
		System.out.println(userName);
		System.out.println();
		System.out.println();
		long count = (Long) cr.uniqueResult();
		if (count == 1) {
			SignupUser p = searchBySignupUserName(signupUser.getUserName(), signupUser.getPassword(),signupUser.getCategory());
			System.out.println(signupUser.getUserName());
			System.out.println(signupUser.getPassword());
			System.out.println(userName);
			
			System.out.println();
	        if (Category.STAFF.equals(p.getCategory())) {
	            sessionMap.put("UserInfo", p);
	            sig = p;
	            System.out.println("Staff Login Success");
	            return "ParkingMenu.xhtml?faces-redirect=true";
	        } else if (Category.ADMIN.equals(p.getCategory())) {
	            sessionMap.put("UserInfo", p);
	            sig = p;
	            System.out.println("Admin Login Success");
	            return "AdminMenu.xhtml?faces-redirect=true";
	        } else {
	            System.out.println("Invalid Category");
	        }
	       
		} else {
			System.out.println("Login Failed");
			
    	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid username or password or select correct category.", null));

			return null;
		}
		return null;
		
	}
	
	

	public SignupUser searchBySignupUserName(String username, String password ,Category category ) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
//		Session session = ConnectionObject.getSession();
		Criteria cr = session.createCriteria(SignupUser.class);
		cr.add(Restrictions.eq("userName", username));
		cr.add(Restrictions.eq("password", EntryptPassword.getCode(password)));
		cr.add(Restrictions.eq("category", category));

		SignupUser signupUser = (SignupUser) cr.uniqueResult();
		return signupUser;
	}*/


	
	public List<SignupUser> searchBySignupUserNames(String username, String securityquestions1, String securityAnswer1, String securityquestions2, String securityAnswer2) {
	    SessionFactory sf = SessionHelper.getConnection();
	    Session session = sf.openSession();
//	    Session session = ConnectionObject.getSession();
	    Criteria cr = session.createCriteria(SignupUser.class);
	    cr.add(Restrictions.eq("userName", username));
	    cr.add(Restrictions.eq("securityquestions1", securityquestions1));
	    cr.add(Restrictions.eq("securityAnswer1", securityAnswer1));
	    cr.add(Restrictions.eq("securityquestions2", securityquestions2));
	    cr.add(Restrictions.eq("securityAnswer2", securityAnswer2));
	   
	    
	    List<SignupUser> signupUsers = cr.list();
	    session.close();

	    return signupUsers;
	}
	
	
	
	
	
	
/*	
	public void resetPas(String username, String newPassword, String oldpassword) {
		
		SessionFactory sf = SessionHelper.getConnection();
	    // Search for the user using the provided username and security questions/answers
		Session session1 = ConnectionObject.getSession();
	    List<SignupUser> users = (List<SignupUser>) session1.createQuery("FROM SignupUser WHERE userName='" + username + "'").list();
	    System.out.println(users.size());
	    if (users.size() != 0) {
	        SignupUser user = users.get(0);
	        // Check if the old password matches the user's current password
	        System.out.println(oldpassword);
	        System.out.println(user.getPassword());
	        
	        if (oldpassword.equals(user.getPassword())) {
	            // Check if the new password matches any of the last three passwords
	        	System.out.println("disu");
	        	
	            if (isLastThreePasswords(user, newPassword) == false) {
	            	System.out.println("disfsdfu");
	                Transaction transaction = null;
	                Session session = null;

	                try {
	                    session = ConnectionObject.getSession();
	                    transaction = session.beginTransaction();

	                    user.setOldPassword2(user.getOldPassword1());
	                    user.setOldPassword1(user.getPassword());
	                    user.setPassword(newPassword);

	                    session.update(user);
	                    transaction.commit();
	    	    	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password has been reset successfully.", null));

	                    // You can also redirect to a success page or perform any other necessary action upon successful password reset.
	                } catch (Exception e) {
	                    if (transaction != null) {
	                        transaction.rollback();
	                    }
	    	    	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "An error occurred during password reset.", null));

	                    // Handle the exception appropriately (e.g., log the error, show an error message)
	                } finally {
	                    if (session != null) {
	                        session.close();
	                    }
	                }
	            } else {
	            	            	                        	
		    	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "The new password matches one of the last three passwords.", null));

	            }
	        } else {
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "The old password does not match the current password.", null));
	        }
	    } else {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  "User not found or security questions/answers do not match.",null));
	    }
	}

	
	*/
	
	
	public String resetPas(String username, String newPassword, String oldPassword) {
	    try {
	   //     SessionFactory sf = SessionHelper.getConnection();
	        // Search for the user using the provided username and security questions/answers
	        Session session1 = ConnectionObject.getSession();
	        List<SignupUser> users = (List<SignupUser>) session1.createQuery("FROM SignupUser WHERE userName='" + username + "'").list();
	        
	        if (users.size() != 0) {
	            SignupUser user = users.get(0);
	            
	            // Check if the old password matches the users current password
	            if (oldPassword.equals(user.getPassword())) {
	                // Check if the new password matches any of the last three passwords
	                  
	                
	                if (!isValidPassword1(newPassword)) {
		           	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid password format. Please choose a different password.", null));

	                	 return "Invalid password format. Please choose a different password.";
	                }
	                
	                if (isLastThreePasswords(user, newPassword)) {
		           	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "The new password matches one of the last three passwords.", null));

	                	 return "The new password matches one of the last three passwords.";
	                }
	                
	                Transaction transaction = null;
	                Session session = null;
	                
	                try {
	                    session = ConnectionObject.getSession();
	                    transaction = session.beginTransaction();
	                    
	                    user.setOldPassword2(user.getOldPassword1());
	                    user.setOldPassword1(user.getPassword());
	                    user.setPassword(newPassword);
	                    
	                    session.update(user);
	                    transaction.commit();
	           	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password has been reset successfully.", null));

	                    return "Password has been reset successfully.";
	                } catch (Exception e) {
	                    if (transaction != null) {
	                        transaction.rollback();
	                    }
	                    e.printStackTrace();
	  				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "An error occurred during password reset.", null));

	                    return"An error occurred during password reset.";
	                } finally {
	                    if (session != null) {
	                        session.close();
	                    }
	                }
	            } else {
	  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "The old password does not match the current password.", null));

	            	return"The old password does not match the current password.";
	            }
	        } 
	    } catch (Exception e) {
	        e.printStackTrace();
	   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "An error occurred during password reset.", null));

	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("An error occurred during password reset."));
	    }
		return null;
	}

	
	
	
	
	
	private boolean isLastThreePasswords(SignupUser user, String newPassword) {
	    if(newPassword.equals(user.getOldPassword1()) || newPassword.equals(user.getOldPassword2()) || newPassword.equals(user.getPassword())){
	    	return true;
	    }
		
	    return false;
	}


	
	    private boolean isValidPassword1(String password) {
	        
	        // Sample implementation (password should be at least 8 characters long and contain at least one uppercase letter and one digit):
	        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*\\d.*");
	    }

	    
	    
	    
	    
	 /*   
	    
	    public String forgotPassword(String username, String newPassword, String oldpassword) {
	    	try {
	           SessionFactory sf = SessionHelper.getConnection();
	            // Search for the user using the provided username and security questions/answers
	           Session session1 = sf.openSession();
	           System.out.println("In the Method");
	            List<SignupUser> users = (List<SignupUser>) session1.createQuery("FROM SignupUser WHERE userName='" + username + "'").list();
	            System.out.println("User's List"+users);
	            System.out.println("User List Size"+users.size());

	         if (users.size() == 0) {
	           	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid username or security answers.", null));
	            System.out.println("List Size Is Zero");
	            }
	           


	            SignupUser user = users.get(0);
	            System.out.println(user);
	            

	            // Compare the provided answers with the stored answers
	            if (user.getSecurityAnswer1().equals(securityAnswer1) && user.getSecurityAnswer2().equals(securityAnswer2)  && user.getSecurityquestions1().equals(securityquestions1)  && user.getSecurityquestions2().equals(securityquestions2)) {
	            	
	            	
	            	System.out.println("securityAnswers");
	            	System.out.println(securityAnswer1);
	            	System.out.println(securityAnswer2);
	            	
	                // Security answers are correct, allow password reset

	                // Validate the new password according to your criteria
	                if (!isValidPassword1(newPassword)) {
		           	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid password format. Please choose a different password.", null));
		           	    System.out.println("New Password"+newPassword);

	                }

	                // Check if the new password matches any of the last three passwords
	                if (isLastThreePasswords(user, newPassword)) {
		           	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "The new password matches one of the last three passwords.", null));
	                    return "The new password matches one of the last three passwords.";

	                }

	                Transaction transaction = null;
	                Session session = null;

	                try {
	                	  session = sf.openSession();	        
	                	  transaction = session.beginTransaction();

	                    user.setOldPassword2(user.getOldPassword1());
	                    user.setOldPassword1(user.getPassword());
	                    user.setPassword(newPassword);

	                    session.update(user);
	                    transaction.commit();
		           	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password has been reset successfully.", null));
	                    return "Your password has been changed successfully!";

	                    	
	                } catch (Exception e) {
	                    if (transaction != null) {
	                        transaction.rollback();
	                    }
	                    e.printStackTrace();
		  				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "An error occurred during password reset.", null));

	                    return "An error occurred during changing password .";

	                } finally {
	                    if (session != null) {
	                        session.close();
	                    }
	                }
	            } else {
	           	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid username or security answers.", null));

	                return null;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
  				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "An error occurred during password reset.", null));

                return null;
	        }
	    }*/

	   
	    /*
	    
	 public String forgotPassword(String username, String newPassword, String oldpassword) {
	        try {
	            SessionFactory sf = SessionHelper.getConnection();
	            // Search for the user using the provided username and security questions/answers
	            Session session1 = ConnectionObject.getSession();
	            List<SignupUser> users = (List<SignupUser>) session1.createQuery("FROM SignupUser WHERE userName='" + username + "'").list();

	            if (users.size() == 0) {
	           	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid username or security answers.", null));

	                return "Invalid username or security answers.";
	            }

	            SignupUser user = users.get(0);

	            // Compare the provided answers with the stored answers
	            if (user.getSecurityAnswer1().equals(securityAnswer1) && user.getSecurityAnswer2().equals(securityAnswer2)  && user.getSecurityquestions1().equals(securityquestions1)  && user.getSecurityquestions2().equals(securityquestions2)) {
	                // Security answers are correct, allow password reset

	                // Validate the new password according to your criteria
	                if (!isValidPassword1(newPassword)) {
		           	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid password format. Please choose a different password.", null));

	                    return "Invalid password format. Please choose a different password.";
	                }

	                // Check if the new password matches any of the last three passwords
	                if (isLastThreePasswords(user, newPassword)) {
		           	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "The new password matches one of the last three passwords.", null));

	                    return "The new password matches one of the last three passwords.";
	                }

	                Transaction transaction = null;
	                Session session = null;

	                try {
	                    session = ConnectionObject.getSession();
	                    transaction = session.beginTransaction();

	                    user.setOldPassword2(user.getOldPassword1());
	                    user.setOldPassword1(user.getPassword());
	                    user.setPassword(newPassword);

	                    session.update(user);
	                    transaction.commit();
		           	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password has been reset successfully.", null));

	                    return "Your password has been changed successfully!";
	                } catch (Exception e) {
	                    if (transaction != null) {
	                        transaction.rollback();
	                    }
	                    e.printStackTrace();
		  				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "An error occurred during password reset.", null));

	                    return "An error occurred during changing password .";
	                } finally {
	                    if (session != null) {
	                        session.close();
	                    }
	                }
	            } else {
	           	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid username or security answers.", null));

	                return "Invalid username or security answers.";
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
  				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "An error occurred during password reset.", null));

	            return "An error occurred during changing password .";
	        }
	    }

	    */

	    
	public String forgotPassword(String username, String newPassword, String oldpassword) {

		try {
			SessionFactory sf = SessionHelper.getConnection();
			// Search for the user using the provided username and security
			// questions/answers
			Session session1 = sf.openSession();
			System.out.println("In the Method");

			List<SignupUser> users = (List<SignupUser>) session1
					.createQuery("FROM SignupUser WHERE userName='" + username + "'").list();
			System.out.println("User's List" + users);
			System.out.println("User List Size" + users.size());
			if (users.size() == 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid username or security answers.", null));

				return "Invalid username or security answers.";
			}

			SignupUser user = users.get(0);
			System.out.println(user);

			System.out.println(user.getUserName());
			System.out.println(user.getSecurityAnswer1());

			System.out.println(user.getSecurityAnswer1());
			System.out.println(user.getSecurityAnswer2());
			System.out.println(user.getSecurityquestions1());
			System.out.println(user.getSecurityquestions2());
			System.out.println(securityAnswer1);
			System.out.println(securityAnswer2);
			System.out.println(securityquestions1);
			System.out.println(securityquestions2);

			if (user.getSecurityAnswer1().equals(securityAnswer1) && user.getSecurityAnswer2().equals(securityAnswer2)
					&& user.getSecurityquestions1().equals(securityquestions1)
					&& user.getSecurityquestions2().equals(securityquestions2)) {
				// Security answers are correct, allow password reset
				System.out.println("inside");
				System.out.println(user.getUserName());
				System.out.println(user.getSecurityAnswer1());
				System.out.println("securityAnswer1:" + user.getSecurityAnswer1());

				System.out.println("securityAnswers");
				System.out.println(securityAnswer1);
				System.out.println(securityAnswer2);

				// Validate the new password according to your criteria
				if (!isValidPassword1(newPassword)) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Invalid password format. Please choose a different password.", null));

					return "Invalid password format. Please choose a different password.";
				}

				// Check if the new password matches any of the last three
				// passwords
				if (isLastThreePasswords(user, newPassword)) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							"The new password matches one of the last three passwords.", null));

					return "The new password matches one of the last three passwords.";
				}

				Transaction transaction = null;
				Session session = null;

				try {
					session = sf.openSession();
					transaction = session.beginTransaction();

					user.setOldPassword2(user.getOldPassword1());
					user.setOldPassword1(user.getPassword());
					user.setPassword(newPassword);

					session.update(user);
					transaction.commit();
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Password has been reset successfully.", null));

					return "Your password has been changed successfully!";
				} catch (Exception e) {
					if (transaction != null) {
						transaction.rollback();
					}
					e.printStackTrace();
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							"An error occurred during password reset.", null));

					return "An error occurred during changing password .";
				} finally {
					if (session != null) {
						session.close();
					}
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid username or security answers.", null));

				return "Invalid username or security answers.";
			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "An error occurred during password reset.", null));

			return "An error occurred during changing password .";
		}
	}

	public static void main(String[] args) {

	}
	    
}
	
	

	
	
