package com.wrungel.example;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.support.Expressions;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static com.mysema.query.support.Expressions.cases;

public class QueryDSLTest {

    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeClass
    public static void beforeClass() throws InterruptedException {
        emf = Persistence.createEntityManagerFactory("foo");
    }

    @Before
    public void before() {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(new Baa(TestEinheit.TAG_24));
        em.persist(new Baa(TestEinheit.MINUTE));
        em.persist(new Baa(TestEinheit.STUNDE));
    }

    @After
    public void after() {
        try {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }


    @Test
    public void test_case_multiply_QuerySyntaxExceptionUnexpectedToken() {
        QBaa qbaa = QBaa.baa;

        new JPAQuery(em).from(qbaa).list(
                cases().when(qbaa.einheit.eq(TestEinheit.TAG_24)).then(qbaa.id.multiply(10))
                        .when(qbaa.einheit.eq(TestEinheit.MINUTE)).then(qbaa.id.multiply(5L))
                        .otherwise(qbaa.id)
        );
    }

    @Test
    public void constant_test_NPE() {
        QBaa qbaa = QBaa.baa;

        new JPAQuery(em).from(qbaa).map(qbaa.id, Expressions.constant("name"));
    }
}
