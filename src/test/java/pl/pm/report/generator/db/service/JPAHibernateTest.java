package pl.pm.report.generator.db.service;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class JPAHibernateTest {

    protected static EntityManagerFactory emf;
    protected static EntityManager em;

    @BeforeEach
    public void init() throws FileNotFoundException, SQLException {
        emf = Persistence.createEntityManagerFactory("mydb");
        em = emf.createEntityManager();
    }

//    @BeforeEach
//    public void initializeDatabase(){
//        Session session = em.unwrap(Session.class);
//        session.doWork(new Work() {
//            @Override
//            public void execute(Connection connection) throws SQLException {
//                try {
//                    File script = new File(getClass().getResource("/data.sql").getFile());
//                    RunScript.execute(connection, new FileReader(script));
//                } catch (FileNotFoundException e) {
//                    throw new RuntimeException("could not initialize with script");
//                }
//            }
//        });
//    }

    @AfterEach
    public void tearDown(){
        em.clear();
        em.close();
        emf.close();
    }
}
