package pl.pm.report.generator.db;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtilities {

    private static volatile PersistenceUtilities instance;
    private final Map<String, EntityManagerFactory> emfMap = new HashMap<>();

    private PersistenceUtilities() {
    }

    public static PersistenceUtilities getInstance() {
        if (instance == null) {
            synchronized (PersistenceUtilities.class) {
                if (instance == null) {
                    instance = new PersistenceUtilities();
                }
            }
        }
        return instance;
    }

    public boolean isConfigured(String persistenceUnitName) {
        return emfMap.containsKey(persistenceUnitName);
    }

    public EntityManagerFactory getEntityManagerFactory(String persistenceUnitName, Map<String, String> emfSettings) {
        if (emfMap.get(persistenceUnitName) == null) {
            synchronized (this) {
                if (emfMap.get(persistenceUnitName) == null) {
                    EntityManagerFactory emf = null;
                    if (emfSettings == null) {
                        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
                    } else {
                        emf = Persistence.createEntityManagerFactory(persistenceUnitName, emfSettings);
                    }
                    emfMap.put(persistenceUnitName, emf);
                }
            }
        }
        return emfMap.get(persistenceUnitName);
    }
}
