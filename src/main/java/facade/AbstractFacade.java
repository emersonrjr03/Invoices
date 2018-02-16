package facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import model.Invoice;
import model.InvoiceItem;

public abstract class AbstractFacade<T> {
	private Class<T> entityClass;

	public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
	
    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
    	if(entity != null) {
    		getEntityManager().persist(entity);	
    	}
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
    
    @Transactional
    public void remove(T entity) {
        if(entity instanceof InvoiceItem) {
        	InvoiceItem item = getEntityManager().find(InvoiceItem.class, ((InvoiceItem)entity).getId());
        	Invoice invoice = item.getInvoice();
        	invoice.getInvoiceItens().remove(item);
        	getEntityManager().flush();
        }
        getEntityManager().remove(getEntityManager().merge(entity));
        
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    
    public List<T> findByField(String field, Object value){
    	CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    	CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        
        cq.select(root);
        ParameterExpression<Long> p = cb.parameter(Long.class);
        cq.select(root).where(cb.equal(root.get(field), p));
        
        TypedQuery<T> query = getEntityManager().createQuery(cq);
        query.setParameter(1, value);
        return getEntityManager().createQuery(cq).getResultList();
        
    }
    
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
