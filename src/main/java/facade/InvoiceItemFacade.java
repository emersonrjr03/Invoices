package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.InvoiceItem;
@Stateless
public class InvoiceItemFacade extends AbstractFacade<InvoiceItem>{

	@PersistenceContext(unitName = "InvoicesPU")
	private EntityManager em;
	
	public InvoiceItemFacade() {
		super(InvoiceItem.class);
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
