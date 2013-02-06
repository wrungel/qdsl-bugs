package casebuilder;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.CaseBuilder;
import com.mysema.query.types.expr.NumberExpression;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


public class CaseBuilderTest {


    @Test
    public void testCaseBuilder() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Buch buch1 = new Buch();
        buch1.setGenre(Genre.history);
        em.persist(buch1);

        Buch buch2 = new Buch();
        buch2.setGenre(Genre.since_fiction);
        em.persist(buch2);


        QBuch qBuch = QBuch.buch;

        CaseBuilder caseBuilder = new CaseBuilder();


        NumberExpression<Integer> numberExpression = caseBuilder.when(qBuch.genre.ne(Genre.history)).then(99)
                .otherwise(88);

        List<Integer> result = new JPAQuery(em).from(qBuch).list(numberExpression);

        Assert.assertThat(result, Matchers.hasSize(2));
        Assert.assertThat(result, Matchers.contains(99));
        Assert.assertThat(result, Matchers.contains(88));


        em.getTransaction().commit();

    }
}
