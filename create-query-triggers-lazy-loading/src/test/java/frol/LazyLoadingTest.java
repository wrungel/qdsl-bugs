package frol;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LazyLoadingTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static Logger logger = LoggerFactory.getLogger("TEST");
    private static QEreignis qEreignis = QEreignis.ereignis;

    @BeforeClass
    public static void beforeClass() {
        emf = Persistence.createEntityManagerFactory("test");
    }

    @Test
    public void test() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        createEreignisAndChild();

        em.flush();
        em.clear();

        Ereignis ereignis = new JPAQueryFactory(em).selectFrom(qEreignis).fetch().get(0);

        JPAQuery<Ereignis> jpaQuery = new JPAQueryFactory(em).selectFrom(qEreignis)
                .where(qEreignis.eq(ereignis));

        assertThat(isLoaded(ereignis, "otherChildren"), is(false));
        assertThat(isLoaded(ereignis, "toStringChildren"), is(false));
        logger.debug("*** before createQuery() called");
        jpaQuery.createQuery();
        logger.debug("*** after createQuery() called");
        assertThat(isLoaded(ereignis, "otherChildren"), is(false));
        assertThat(
                "createQuery() has triggered lazy loading of toStringChildren property!",
                isLoaded(ereignis, "toStringChildren"), is(false));
    }


    private static boolean isLoaded(Object entity, String propertyName) {
        return Persistence.getPersistenceUtil().isLoaded(entity, propertyName);
    }

    private void createEreignisAndChild() {
        Ereignis ereignis = new Ereignis();
        ereignis.getToStringChildren().add(new Child());
        ereignis.getOtherChildren().add(new Child());
        em.persist(ereignis);
    }
}
