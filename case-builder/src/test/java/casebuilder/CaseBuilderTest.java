package casebuilder;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.CaseBuilder;
import com.mysema.query.types.expr.NumberExpression;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;


public class CaseBuilderTest {


    EntityManagerFactory emf;
    EntityManager em;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();


        em.getTransaction().begin();
        Buch buch1 = new Buch();
        buch1.setName("nupsi");
        buch1.setGenre(Genre.history);
        em.persist(buch1);

        Buch buch2 = new Buch();
        buch1.setName("uschi");
        buch2.setGenre(Genre.since_fiction);
        em.persist(buch2);
    }

    @After
    public void tearDown() {
        em.getTransaction().rollback();
    }

    @Test
    public void testHibernateCase() {
        String queryString = "select case when (buch.genre <> ?1) then ?2 else ?3 end from Buch buch";
        Query query = em.createQuery(queryString);
        query.setParameter(1, Genre.history);
        query.setParameter(2, new Integer(1));
        query.setParameter(2, new Integer(2));
    }

    @Test
    public void testCaseInSelect() {
        String queryString = "select case when buch.name = 'nupsi' then 1 else 2 end from Buch buch";
        Query query = em.createQuery(queryString);
        List<Integer> resultList = query.getResultList();
        Assert.assertThat(resultList, Matchers.hasSize(2));
//        Assert.assertThat(resultList, Matchers.contains(1));
//        Assert.assertThat(resultList, Matchers.contains(2));
    }

    @Test
    public void testCaseBuilder() {



        QBuch qBuch = QBuch.buch;

        CaseBuilder caseBuilder = new CaseBuilder();


        NumberExpression<Integer> numberExpression = caseBuilder.when(qBuch.genre.eq(Genre.history)).then(99).otherwise(88);
        List<Integer> result = new JPAQuery(em).from(qBuch).list(numberExpression);

        Assert.assertThat(result, Matchers.hasSize(2));
        Assert.assertThat(result, Matchers.contains(99));
        Assert.assertThat(result, Matchers.contains(88));


    }
}
