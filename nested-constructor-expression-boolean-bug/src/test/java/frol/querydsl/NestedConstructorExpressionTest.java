package frol.querydsl;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.ConstructorExpression;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class NestedConstructorExpressionTest {

    static EntityManagerFactory emf;

    EntityManager em;
    JPAQuery jpaQuery;
    QVideo qVideo = QVideo.video;
    QBook qBook = QBook.book;

    @BeforeClass
    public static void beforeClass() {
        emf = Persistence.createEntityManagerFactory("h2");
    }

    @Before
    public void before() {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(new Video());
        em.flush();
        em.clear();

        jpaQuery = new JPAQuery(em).from(qVideo);
        jpaQuery.leftJoin(qVideo.book, qBook);

    }

    @After
    public void after() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
        em.close();
    }

    @Test//(expected = IllegalArgumentException.class)
    public void nested_constructor_expression_with_boolean_parameter_cause_exception() {
        jpaQuery.list(
                ConstructorExpression.create(VideoPreviewDTO.class, qVideo.id,
                        ConstructorExpression.create(BookPreviewDTO.class, qBook.id, qBook.good)));
    }

    @Test
    public void nested_constructor_expression_without_boolean_parameter_doesnt_cause_exception() {
        List<VideoPreviewDTO> result = jpaQuery.list(
                ConstructorExpression.create(VideoPreviewDTO.class, qVideo.id,
                        ConstructorExpression.create(BookPreviewDTO.class, qBook.id)));
        assert result.size() == 1;
    }

}
