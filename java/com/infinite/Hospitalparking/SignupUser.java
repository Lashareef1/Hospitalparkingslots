package com.infinite.Hospitalparking;



import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "registration")
@ManagedBean(name = "Signup")
@SessionScoped

public class SignupUser {
	
	@Id
	@GenericGenerator(name = "custom_generator", strategy = "com.infinite.Hospitalparking.Uidgenerator")
	@GeneratedValue(generator = "custom_generator")
	
	@Column (name="UserId")
	private String userid;

	@Column(name = "Name")
	private String name;


	@Column(name = "MobileNo")
	private String mobileNo;
	

	@Column(name = "Dob")
	private Date dob;

	@Enumerated(EnumType.STRING)
	@Column(name = "Category")
	private  Category category;

	@Column(name = "UserName")
	private String userName;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "Password")
	private String password;
	
	@Column(name = "SecurityQuestions1")
	private String securityquestions1;
	
	@Column(name = "SecurityAnswer1")
	private String securityAnswer1;
	
	@Column(name = "SecurityQuestions2")
	private String securityquestions2;
	
	@Column(name = "SecurityAnswer2")
	private String securityAnswer2;
	
	@Column(name ="oldPassword1")
	private String oldPassword1;
	
	
	
	@Column(name ="oldPassword2")
	private String oldPassword2;
	

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Category[] getCategoryop() {
		 return Category.values();
		 
		 }
	
 
	
	

	public String getOldPassword1() {
		return oldPassword1;
	}

	public void setOldPassword1(String oldPassword1) {
		this.oldPassword1 = oldPassword1;
	}

	public String getOldPassword2() {
		return oldPassword2;
	}

	public void setOldPassword2(String oldPassword2) {
		this.oldPassword2 = oldPassword2;
	}

	public List<String> getLastThreePasswords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "SignupUser [userid=" + userid + ", name=" + name + ", mobileNo=" + mobileNo + ", dob=" + dob
				+ ", category=" + category + ", userName=" + userName + ", email=" + email + ", password=" + password
				+ ", securityquestions1=" + securityquestions1 + ", securityAnswer1=" + securityAnswer1
				+ ", securityquestions2=" + securityquestions2 + ", securityAnswer2=" + securityAnswer2
				+ ", oldPassword1=" + oldPassword1 + ", oldPassword2=" + oldPassword2 + "]";
	}


	
	public void validatePassword() {
        // Your regular expression pattern
        String regexPattern = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";

        // Check if the password matches the regular expression pattern
        if (!password.matches(regexPattern)) {
            // Set the custom validation message for invalid password
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("form:password", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password must contain: one Uppercase, one lowercase, one digit, one special char, must be at least 8 char long.", null));
        }
    }
	
	}



