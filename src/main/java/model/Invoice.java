package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import listeners.InvoiceListener;

@Entity
@EntityListeners(InvoiceListener.class)
@Table(name = "invoice", schema="invoices")
@SequenceGenerator(name = "WEB_SEQINVOICE", sequenceName = "WEB_SEQINVOICE", allocationSize = 1)
@NamedQueries({
    @NamedQuery(name="Invoice.findByCustomer",							query="SELECT i FROM Invoice i WHERE i.customerName LIKE :customername"),
    @NamedQuery(name="Invoice.findBetweenIssueDatesPeriod", 			query="SELECT i FROM Invoice i WHERE i.issueDate BETWEEN :iniDate AND :finDate"),
    @NamedQuery(name="Invoice.findBetweenIssueDatesPeriodByCustomer",	query="SELECT i FROM Invoice i WHERE i.issueDate BETWEEN :iniDate AND :finDate AND i.customerName = :customername"),
    @NamedQuery(name="Invoice.findBetweenDueDatesPeriod", 				query="SELECT i FROM Invoice i WHERE i.dueDate BETWEEN :iniDate AND :finDate"),
    @NamedQuery(name="Invoice.findBetweenDueDatesPeriodByCustomer",		query="SELECT i FROM Invoice i WHERE i.dueDate BETWEEN :iniDate AND :finDate AND i.customerName = :customername"),
    @NamedQuery(name="Invoice.findPendingInvoices",		query="SELECT i FROM Invoice i WHERE i.payDate IS NULL"),
    @NamedQuery(name="Invoice.findPaidInvoices",		query="SELECT i FROM Invoice i WHERE i.payDate IS NOT NULL")
})
public class Invoice implements Serializable{

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "WEB_SEQINVOICE")
	@Column(name="invoice_id")
	private Long id;
	
	@Column(length = 60, nullable = false)
	private String customerName;
	
	@OneToMany(mappedBy = "invoice", targetEntity = InvoiceItem.class, orphanRemoval = true, cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<InvoiceItem> invoiceItens;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "issuedate", nullable = false)
	private Date issueDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "duedate", nullable = false)
	private Date dueDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "paydate", nullable = true)
	private Date payDate;
	
	@Column(length = 120, nullable = true)
	private String comments;
	
	private Double totalValue;



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<InvoiceItem> getInvoiceItens() {
		return invoiceItens;
	}
	public void setInvoiceItens(List<InvoiceItem> invoiceItens) {
		this.invoiceItens = invoiceItens;
	}
	

	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	

	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@Column(precision = 10, scale = 2, nullable = true)
	public Double getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}
	
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
		Invoice other = (Invoice) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
