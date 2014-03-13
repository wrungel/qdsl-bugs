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

public class ConstructorExpressionWithJoinedBooleanTest {

    static EntityManagerFactory emf;

    EntityManager em;
    JPAQuery jpaQuery;
    QVideo qVideo = QVideo.video;
    QBook qBook = QBook.book;
    Long videoId;

    @BeforeClass
    public static void beforeClass() {
        emf = Persistence.createEntityManagerFactory("h2");
    }

    @Before
    public void before() {
        em = emf.createEntityManager();
        em.getTransaction().begin();


        Video video = new Video();
        em.persist(video);

        em.flush();
        em.clear();

        videoId = video.getId();
        jpaQuery = new JPAQuery(em).from(qVideo);
        jpaQuery.leftJoin(qVideo.book, qBook);

    }

    @After
    public void after() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
        em.close();
    }

    /**
     * This test does not fail
     */
    @Test
    public void book_attached_to_video_and_constructor_expr_with_joined_boolean_param() {
        Book book = new Book();
        em.persist(book);
        Video video = em.find(Video.class, videoId);
        video.setBook(book);
        List<VideoPreviewDTO> result = jpaQuery.list(
                ConstructorExpression.create(VideoPreviewDTO.class, qVideo.id, qBook.good));
        assert result.size() == 1;
    }


    @Test//(expected = IllegalArgumentException.class)
    public void constructor_expression_with_joined_boolean_parameter() {
        jpaQuery.list(
                ConstructorExpression.create(VideoPreviewDTO.class, qVideo.id, qBook.good));
    }

    @Test
    public void constructor_expression_with_joined_string_parameter() {
        jpaQuery.list(
                ConstructorExpression.create(VideoPreviewDTO.class, qVideo.id, qBook.title));
    }

    @Test//(expected = IllegalArgumentException.class)
    public void nested_constructor_expression_with_joined_string_and_boolean_parameter() {
        List<VideoPreviewDTO> result = jpaQuery.list(
                ConstructorExpression.create(VideoPreviewDTO.class, qVideo.id, qBook.good, qBook.title));
        assert result.size() == 1;
    }



}
