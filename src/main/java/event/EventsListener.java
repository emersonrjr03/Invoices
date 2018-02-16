package event;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.SaveOrUpdateEvent;

import model.InvoiceItem;

public class EventsListener{

	@PostPersist
	public void onSaveOrUpdate(SaveOrUpdateEvent e) throws HibernateException {
		Object obj = e.getEntity();
		if(obj instanceof InvoiceItem) {
			InvoiceItem item = (InvoiceItem) obj;
			System.out.println(e.getSession().getSessionFactory().getClass().toString());
		}
		
	}

}
