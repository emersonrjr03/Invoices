package listeners;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import model.Invoice;
import model.InvoiceItem;

public class InvoiceItemListener {
	@PostPersist
	public void methodInvokedAfterPersist(InvoiceItem item) {
		System.out.println("Persisted Item: " + item.getProduct());
	}
	@PostUpdate
	public void methodInvokedAfterUpdate(InvoiceItem item) {
		System.out.println("Updated Item: " + item.getProduct());
	}
	@PostRemove
	public void methodInvokedAfterRemove(InvoiceItem item) {
		System.out.println("Removed Item: " + item.getProduct() );
	}

}
