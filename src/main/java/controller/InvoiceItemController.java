package controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import antlr.StringUtils;
import facade.InvoiceFacade;
import facade.InvoiceItemFacade;
import model.Invoice;
import model.InvoiceItem;

@ManagedBean(name="invoiceItemController")
@SessionScoped
public class InvoiceItemController {

	@EJB
	private InvoiceItemFacade invoiceItemFacade;
	private InvoiceItem invoiceItem;

	private Double totalItem;
	
	public InvoiceItemController() {
		this.invoiceItem = new InvoiceItem();
	}
	
	public List<InvoiceItem> listAll(){
		return invoiceItemFacade.findAll();
	}
	
	public void addInvoiceItem(Invoice i) {
		if(invoiceItem.getId() != null) {
			editInvoiceItem();
		} else {
			invoiceItem.setInvoice(i);
			invoiceItemFacade.create(invoiceItem);	
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Item created successfully."));	
		}
		this.invoiceItem = new InvoiceItem();
	}
	
	public String prepareEditInvoiceItem(InvoiceItem i) {
		this.invoiceItem = i;
		return "edit";
	}
	public void prepareAddInvoiceItem() {
		this.invoiceItem = new InvoiceItem();
	}
	
	
	public String editInvoiceItem() {
		this.invoiceItemFacade.edit(invoiceItem);
		this.invoiceItem = new InvoiceItem();
		return "index";
	}
	
	public void deleteInvoiceItem(InvoiceItem i) {
		this.invoiceItemFacade.remove(i);
	}
	
	public InvoiceItem getInvoiceItem() {
		return invoiceItem;
	}

	public void setInvoiceItem(InvoiceItem invoiceItem) {
		this.invoiceItem = invoiceItem;
	}
	
	public void calcTotalItem() {
		BigDecimal valorExato = new BigDecimal(getInvoiceItem().getUnitPrice() * getInvoiceItem().getQuantity())
		        .setScale(2, RoundingMode.HALF_DOWN);
		this.invoiceItem.setTotalItem(valorExato.doubleValue());	
	}
	
	public Double getTotalItem() {
		return this.totalItem;
	}
	public void setTotalItem(Double totalItem) {
		this.totalItem = totalItem;
	}

	public InvoiceItemFacade getInvoiceItemFacade() {
		return invoiceItemFacade;
	}

	public void setInvoiceItemFacade(InvoiceItemFacade invoiceItemFacade) {
		this.invoiceItemFacade = invoiceItemFacade;
	}
}
