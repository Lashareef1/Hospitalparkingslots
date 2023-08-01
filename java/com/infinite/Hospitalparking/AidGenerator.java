package com.infinite.Hospitalparking;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class AidGenerator implements IdentifierGenerator{

	
	@Override
	public Serializable generate(SessionImplementor arg0, Object arg1) throws HibernateException {
		
		System.out.println("In the Admin ID Generator");
		SessionFactory sf=SessionHelper.getConnection();
		Session session =sf.openSession();
		Criteria cr=session.createCriteria(AdminBooking.class);
		
		List<AdminBooking> cList=cr.list();
		System.out.println(cr.list());
		System.out.println("List Size is"+cList.size());
		if(cList.size()==0){
			String strfound="A001";
			return strfound;
		}else {
			System.out.println("In else case");
			String strfound=cList.get(cList.size()-1).getAdminId();

			
			String sub=strfound.substring(1);
			
			int temp=Integer.parseInt(sub);
			
			temp=temp+1;
			
			strfound=String.format("A%03d", temp);
			System.out.println("New ID Generated is:"+strfound);
			
			return strfound;
			
		}
		
	}
}

