package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import event.EventsListener;
import listeners.InvoiceItemListener;

@Entity
@EntityListeners(InvoiceItemListener.class)
@Table(name = "invoiceitem", schema="invoices")
@SequenceGenerator(name = "WEB_SEQINVOICEITEM", sequenceName = "WEB_SEQINVOICEITEM", allocationSize = 1)
@NamedQueries({
    @NamedQuery(name="InvoiceItem.findAllItemsInvoice", query="SELECT i FROM InvoiceItem i WHERE i.invoice.id LIKE :invoice_id")})
public class InvoiceItem implements Serializable {

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "WEB_SEQINVOICEITEM")
	@Column(name="invoiceitem_id")
	private Long id;
	
	@ManyToOne(optional=false,fetch=FetchType.EAGER)
	@JoinColumn(name="invoice_id")
	private Invoice invoice;
	private String product;
	private double unitPrice;
	private int quantity;
	private double totalItem;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}

	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(double totalItem) {
		this.totalItem = totalItem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceItem other = (InvoiceItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
