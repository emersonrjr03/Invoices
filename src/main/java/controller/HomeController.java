package controller;

import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import facade.InvoiceFacade;
import model.Invoice;
import model.InvoiceItem;

@ManagedBean
@ViewScoped
public class HomeController {

//	@ManagedProperty("#{invoiceController}")
//    private InvoiceController service;
	@PersistenceContext(unitName = "InvoicesPU")
	private EntityManager em;
	
	private LineChartModel chartPendingInvoices;
	
	private LineChartModel chartPaidInvoices;
	@PostConstruct
	public void init() {
		populateChartPendingInvoices();
		populateChartPaidInvoices();
    }
 
    public LineChartModel getChartPendingInvoices() {
        return chartPendingInvoices;
    }
    
    public LineChartModel getChartPaidInvoices() {
        return chartPaidInvoices;
    }
    
	public void populateChartPendingInvoices() {
		chartPendingInvoices = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Pending Invoices");
        ArrayList<Invoice> invoiceList = (ArrayList<Invoice>) em.createNamedQuery("Invoice.findPendingInvoices").getResultList();
        for (Invoice invoice : invoiceList) {
        	series1.set(invoice.getDueDate().toString(), invoice.getTotalValue());
		};
 
        chartPendingInvoices.addSeries(series1);
         
        chartPendingInvoices.setTitle("Pending Invoices");
        chartPendingInvoices.setZoom(true);
        chartPendingInvoices.getAxis(AxisType.Y).setLabel("Invoice Values");
        chartPendingInvoices.getAxis(AxisType.Y).setTickFormat("$ %'.2f");
        DateAxis axis = new DateAxis("Due Dates");
        axis.setTickAngle(-50);
        axis.setMax("2019-02-14");
        axis.setMin("2018-02-01");
        axis.setTickFormat("%b %#d");
         
        chartPendingInvoices.getAxes().put(AxisType.X, axis);
	}
	
	public void populateChartPaidInvoices() {
		chartPaidInvoices = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Paied Invoices");
        ArrayList<Invoice> invoiceList = (ArrayList<Invoice>) em.createNamedQuery("Invoice.findPaidInvoices").getResultList();
        for (Invoice invoice : invoiceList) {
        	series1.set(invoice.getPayDate().toString(), invoice.getTotalValue());
		};
        
 
        chartPaidInvoices.addSeries(series1);
         
        chartPaidInvoices.setTitle("Paid Invoices");
        chartPaidInvoices.setZoom(true);
        chartPaidInvoices.getAxis(AxisType.Y).setLabel("Invoice Values");
        chartPaidInvoices.getAxis(AxisType.Y).setTickFormat("$ %'.2f");
        DateAxis axis = new DateAxis("Pay Dates");
        axis.setTickAngle(-50);
        axis.setMax("2017-02-14");
        axis.setMin("2018-02-14");
        axis.setTickFormat("%b %#d");
        chartPaidInvoices.getAxes().put(AxisType.X, axis);
	}
}
