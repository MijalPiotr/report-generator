package pl.pm.report.generator.db.service;

import java.math.BigInteger;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import pl.pm.report.generator.model.Store;

public class StoreServiceTest  extends JPAHibernateTest {

    @Test
    public void testGetObjectById_fail() {
        Store store = em.find(Store.class, 1);
        assertNull(store);
    }

    @Test
    public void testGetAll_empty() {
        List<Store> store = em.createNativeQuery("SELECT s.* FROM store s", Store.class).getResultList();
        assertEquals(0, store.size());
    }

    @Test
    public void testPersist_success() {
        em.getTransaction().begin();
        em.persist(new Store("buy", 13));
        em.getTransaction().commit();

        List<Store> store = em.createNativeQuery("SELECT * FROM store", Store.class).getResultList();

        assertNotNull(store);
        assertEquals(1, store.size());
    }

    @Test
    public void testDelete_success(){
        em.getTransaction().begin();
        em.persist(new Store("buy", 13));
        em.getTransaction().commit();
        Store store = em.find(Store.class, 1);

        em.getTransaction().begin();
        em.remove(store);
        em.getTransaction().commit();

        List<Store> stores = em.createNativeQuery("SELECT * FROM store", Store.class).getResultList();

        assertEquals(0, stores.size());
    } 

    @Test
    public void testSum_success(){
        em.getTransaction().begin();
        em.persist(new Store("buy", 13));
        em.persist(new Store("supply", 11));
        em.persist(new Store("supply", 2));
        em.persist(new Store("buy", 3));
        em.getTransaction().commit();

        List<BigInteger> buys = em.createNativeQuery("SELECT SUM(amount) FROM store WHERE operationType='buy'").getResultList();
        List<BigInteger> supplys = em.createNativeQuery("SELECT SUM(amount) FROM store WHERE operationType='supply'").getResultList();

        assertEquals(1, buys.size());
        assertEquals(1, supplys.size());
        assertEquals(16, buys.get(0).intValue());
        assertEquals(13, supplys.get(0).intValue());
    }   
}
