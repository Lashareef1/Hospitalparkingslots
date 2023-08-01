package com.infinite.Hospitalparking;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ConnectionObject {

	
	private static SessionFactory sessionFactory;
	 private static Session session;
	 public static Session getSession(){
		 if(sessionFactory==null || sessionFactory.isClosed())
			 sessionFactory=SessionHelper.getConnection();
		 if( session==null || !session.isOpen())
			 session=sessionFactory.openSession();
		 return session;
	 }
	 
}
