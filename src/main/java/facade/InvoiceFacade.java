package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Invoice;

@Stateless
public class InvoiceFacade extends AbstractFacade<Invoice>{
	@PersistenceContext(unitName = "InvoicesPU")
	private EntityManager em;
	
	public InvoiceFacade() {
		super(Invoice.class);
	}
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	
}
