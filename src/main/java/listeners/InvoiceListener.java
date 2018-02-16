package listeners;

import java.math.BigDecimal;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import model.Invoice;
import model.InvoiceItem;

public class InvoiceListener {
	@PostPersist
	public void methodInvokedAfterPersist(Invoice invoice) {
		System.out.println("Persisted Invoice: " + invoice.getCustomerName() );
	}
	@PostUpdate
	public void methodInvokedAfterUpdate(Invoice invoice) {
		System.out.println("Updated Invoice: " + invoice.getCustomerName() );	
	}
	
	@PrePersist
	public void methodInvokedPrePersist(Invoice invoice) {
	}
	
	@PreUpdate
	public void methodInvokedPreUpdate(Invoice invoice) {
	}
	
	@PostRemove
	public void methodInvokedAfterRemove(Invoice invoice) {
		System.out.println("Removed Invoice: " + invoice.getCustomerName() );
	}
	
}
