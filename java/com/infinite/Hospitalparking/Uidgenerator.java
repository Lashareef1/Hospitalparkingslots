package com.infinite.Hospitalparking;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class Uidgenerator implements IdentifierGenerator{
	
	@Override
	public Serializable generate(SessionImplementor arg0, Object arg1) throws HibernateException {
		
		System.out.println("In the ID Generator");
		SessionFactory sf=SessionHelper.getConnection();
		Session session =sf.openSession();
		Criteria cr=session.createCriteria(SignupUser.class);
		
		List<SignupUser> cList=cr.list();
		System.out.println(cr.list());
		System.out.println("List Size is"+cList.size());
		if(cList.size()==0){
			String strfound="U001";
			return strfound;
		}else {
			String strfound=cList.get(cList.size()-1).getUserid();

			
			String sub=strfound.substring(1);
			
			int temp=Integer.parseInt(sub);
			
			temp=temp+1;
			
			strfound=String.format("U%03d", temp);
			
			return strfound;
			
		}
		
	}
}
