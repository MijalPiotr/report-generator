package pl.pm.report.generator.db.service;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import pl.pm.report.generator.db.AbstractService;
import pl.pm.report.generator.model.Store;

public class StoreService extends AbstractService<Store> {

    @Override
    public Store find(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Store.class, id);
    }

    public int selectSumOfOperationBuy() {
        EntityManager em = emf.createEntityManager();
        Query q = em.createNativeQuery("SELECT SUM(amount) FROM store WHERE operationType='buy'");
        List<BigInteger> results = q.getResultList();
        return results.get(0).intValue();
    }

    public int selectSumOfOperationSupply() {
        EntityManager em = emf.createEntityManager();
        Query q = em.createNativeQuery("SELECT SUM(amount) FROM store WHERE operationType='supply'");
        List<BigInteger> results = q.getResultList();
        return results.get(0).intValue();
    }
}
