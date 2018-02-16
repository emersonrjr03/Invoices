package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped.Literal;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.TypedQuery;

import facade.InvoiceFacade;
import model.Invoice;
import model.InvoiceItem;

@ManagedBean
@SessionScoped
public class InvoiceController {

	@EJB
	private InvoiceFacade invoiceFacade;
	private Invoice invoice;
	
	private List<InvoiceItem> invoiceItems;
	
	@ManagedProperty("#{invoiceItemController}")
    private InvoiceItemController service;
		
	private InvoiceItem item;
	
	//Filters
	private Date iniDate;
	private Date finDate;
	private String cusName;
	private Boolean findByDueDate;
	
	public InvoiceController() {
		this.invoice = new Invoice();
		this.item = new InvoiceItem();
	}
	@PostConstruct
	public void init() {

	}
	@PreDestroy
	public void destroy() {
		System.out.println("Being destroied!!");
	}
	public List<Invoice> listAll(){
		return invoiceFacade.findAll();
	}
	
	public List<Invoice> listInvoices(){
		List<Invoice> invoices = new ArrayList<Invoice>();
		findByDueDate = (findByDueDate == null || findByDueDate == false) ? false : true;
		if(iniDate == null && finDate == null && cusName == null) {
			return listAll();
		} else {
			if(finDate == null || iniDate == null) {
				invoices = invoiceFacade.getEm().createNamedQuery("Invoice.findByCustomer").setParameter("customername", cusName).getResultList();
			} else if(cusName == null){
				if(findByDueDate) {
					invoices = invoiceFacade.getEm().createNamedQuery("Invoice.findBetweenDueDatesPeriod").setParameter("iniDate", iniDate).setParameter("finDate", finDate).getResultList();	
				} else {
					invoices = invoiceFacade.getEm().createNamedQuery("Invoice.findBetweenIssueDatesPeriod").setParameter("iniDate", iniDate).setParameter("finDate", finDate).getResultList();
				}
			}else {
				if(findByDueDate) {
					invoices = invoiceFacade.getEm().createNamedQuery("Invoice.findBetweenDueDatesPeriodByCustomer").setParameter("iniDate", iniDate).setParameter("finDate", finDate).getResultList();	
				} else {
					invoices = invoiceFacade.getEm().createNamedQuery("Invoice.findBetweenIssueDatesPeriodByCustomer").setParameter("iniDate", iniDate).setParameter("finDate", finDate).getResultList();
				}
			}	
		}
		return invoices;
	}
	
	public void addInvoice() {
		if(invoice.getId() != null) {
			invoiceFacade.edit(invoice);
		} else {
			invoiceFacade.create(invoice);	
		}
		FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Operation was completed successfully."));	
		invoice = invoiceFacade.find(invoice.getId());
	}
	
	
	public String editInvoice() {
		this.invoiceFacade.edit(invoice);
		this.invoice = new Invoice();
		return "invoiceView";
	}
	
	public void deleteInvoice(Invoice i) {
		this.invoiceFacade.remove(i);
		this.invoice =  new Invoice();
	}
	
	public void addInvoiceItem() {
		if(this.invoice.getId() != null) {
			this.service.getInvoiceItem().setInvoice(this.invoice);
			this.service.addInvoiceItem(this.invoice);
			this.item = new InvoiceItem();
			this.invoice.setInvoiceItens(this.invoiceFacade.getEm().createNamedQuery("InvoiceItem.findAllItemsInvoice").setParameter("invoice_id", this.invoice.getId()).getResultList());
			totalizeItems(this.invoice);
			invoiceFacade.edit(invoice);
		} else {
			 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Insert an invoice before adding items.", "Insert an invoice before adding items."));
		}	
	}
	public void totalizeItems(Invoice invoice) {
		System.out.println("Updating invoice total");
		
		Double totalInvoice = 0.0;
		if(invoice.getInvoiceItens() != null && !(invoice.getInvoiceItens().isEmpty())) {
			for (InvoiceItem i : invoice.getInvoiceItens()) {
				totalInvoice += i.getTotalItem();
			}
		} 
		invoice.setTotalValue(totalInvoice);
	}
	public void removeInvoiceItem(InvoiceItem item) {		
		this.service.deleteInvoiceItem(item);
		this.invoice.setInvoiceItens(this.invoiceFacade.getEm().createNamedQuery("InvoiceItem.findAllItemsInvoice").setParameter("invoice_id", this.invoice.getId()).getResultList());
		totalizeItems(this.invoice);
		invoiceFacade.edit(invoice);
		
	}
	public void prepareDetails(Invoice inv) {
		this.invoice = inv;
	}
	public void prepareAddInvoice() {
		this.invoice = new Invoice();
	}
	public String prepareEditInvoice() {
		return "invoiceEdit";
	}
	
	public List<InvoiceItem> listItems() {
		if(invoice != null && invoice.getId() != null) {
			return invoiceFacade.find(invoice.getId()).getInvoiceItens();
		}
		return null;
	}
	public String backToInvoices() {
		return "invoice";
	}
	
	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public InvoiceFacade getInvoiceFacade() {
		return invoiceFacade;
	}

	public void setInvoiceFacade(InvoiceFacade invoiceFacade) {
		this.invoiceFacade = invoiceFacade;
	}

	public List<InvoiceItem> getInvoiceItems() {
		return invoiceItems;
	}

	public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
		this.invoiceItems = invoiceItems;
	}

	public InvoiceItemController getService() {
		return service;
	}

	public void setService(InvoiceItemController service) {
		this.service = service;
	}
	
	public InvoiceItem getItem() {
		return item;
	}
	public void setItem(InvoiceItem item) {
		this.item = item;
	}
	public Date getIniDate() {
		return iniDate;
	}
	public void setIniDate(Date iniDate) {
		this.iniDate = iniDate;
	}
	public Date getFinDate() {
		return finDate;
	}
	public void setFinDate(Date finDate) {
		this.finDate = finDate;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public Boolean getFindByDueDate() {
		return findByDueDate;
	}
	public void setFindByDueDate(Boolean findByDueDate) {
		this.findByDueDate = findByDueDate;
	}
	

}
