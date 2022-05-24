package pl.pm.report.generator.db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public abstract class AbstractService<T extends Serializable> implements Service<T> {

    protected EntityManagerFactory emf;
    private static final String persistenceUnitName = "mydb";

    public AbstractService() {
        emf = PersistenceUtilities.getInstance().getEntityManagerFactory(persistenceUnitName, null);
    }

    @Override
    public void remove(T item) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(item));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void persist(T item) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void persistAll(List<T> list) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        list.forEach(item -> em.persist(item));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public T merge(T item) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        T merged = em.merge(item);
        em.getTransaction().commit();
        em.close();
        return merged;
    }
}
