package frol;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class SimpleTest {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2");
    private static EntityManager em;
    private static Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    @BeforeClass
    public static void beforeClass() {
        emf = Persistence.createEntityManagerFactory("h2");
    }

    @Test
    public void test() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        createEreignisAndChild();
        createEreignisAndChild();

        em.flush();
        em.clear();


        QEreignis qEreignis = QEreignis.ereignis;
        List<Ereignis> queryResult = new JPAQuery(em).from(qEreignis).list(qEreignis);

        MatcherAssert.assertThat(queryResult, Matchers.hasSize(2));

        logger.info("--- build predicate");
        BooleanBuilder ereignissePredicate = new BooleanBuilder();
        for (List<Ereignis> queryResultSubList : Lists.partition(queryResult, 1000)) {
            ereignissePredicate.or(qEreignis.in(queryResultSubList));
        }
        Query query = new JPAQuery(em).from(qEreignis)
                .join(qEreignis.children)
                .fetch()
                .where(ereignissePredicate)
                .createQuery(qEreignis);

        logger.info("--- Query created");
        List resultList = query.getResultList();
        logger.info("--- getResultList() called");
        queryResult.get(0).getKontext().keySet();
        logger.info("--- property kontext accessed");
        queryResult.get(0).getChildren().size();
    }


    private Ereignis createEreignisAndChild() {
        Ereignis ereignis = new Ereignis();
        Child child = new Child();
        ereignis.getChildren().add(child);

        ereignis.getKontext().put("k1", "v1");
        ereignis.getKontext().put("k2", "v2");


        em.persist(ereignis);
        return ereignis;
    }
}
