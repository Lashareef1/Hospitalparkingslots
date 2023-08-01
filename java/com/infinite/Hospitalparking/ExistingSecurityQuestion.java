package com.infinite.Hospitalparking;




import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.NoResultException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.loader.entity.plan.AbstractLoadPlanBasedEntityLoader;



@RequestScoped
@FacesValidator("com.java.Securityvalidator")
public class ExistingSecurityQuestion implements Validator, Serializable {
	 @Override
	    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	        String question1 = (String) value;
	        String question2 = (String) component.getAttributes().get("firstQuestionDropdown");

	        if (question1 != null && question1.equals(question2)) {
	            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Both security questions cannot be the same."));
	        }
	    }
	}


	/*public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		AbstractLoadPlanBasedEntityLoader currentEntity = (AbstractLoadPlanBasedEntityLoader) comp.getAttributes()
				.get("currentEntity");
		String uniqueColumn = (String) comp.getAttributes().get("uniqueColumn");
		boolean isValid = false;
		try {
			SessionFactory sf = SessionHelper.getConnection();
			Session session = sf.openSession();
			Transaction t = session.beginTransaction();
			Criteria cr = session.createCriteria(SignupUser.class);
			cr.add(Restrictions.eq("securityquestions2", value));
			
			cr.setProjection(Projections.rowCount());
			Long count = (Long) cr.uniqueResult();
			if (count == 1) {
				FacesMessage msg = new FacesMessage("Already this question taken",
						uniqueColumn);
				context.addMessage(comp.getClientId(context), msg);
				throw new ValidatorException(msg);
			}
		} catch (NoResultException ex) {
			isValid = true; // good! no result means unique validation was OK!
		}
	}
}
*/



