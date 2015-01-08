package frol;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.support.Expressions;
import com.mysema.query.types.expr.CaseBuilder;
import com.mysema.query.types.expr.SimpleExpression;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;

public class FooTest {
    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeClass
    public static void beforeClass() {
        emf = Persistence.createEntityManagerFactory("h2");
    }

    @Before
    public void before() {
        em = emf.createEntityManager();
    }

    @After
    public void after() {
        em.close();
    }

    @Test
    public void withoutCaseExpression() {
        JPAQuery jpaQuery = new JPAQuery(em);
        QFoo qFoo = QFoo.foo;
        HashSet<EnumType> enumTypes = new HashSet<EnumType>();
        enumTypes.add(EnumType.B);
        enumTypes.add(EnumType.A);

        jpaQuery.from(qFoo).where(qFoo.enumType.in(enumTypes)).list(qFoo);
    }


    /**
     * Will fail with:
     * java.lang.IllegalArgumentException: Unsupported constant [B, A]
     */
    @Test
    public void caseExpression() {
        JPAQuery jpaQuery = new JPAQuery(em);
        QFoo qFoo = QFoo.foo;
        HashSet<EnumType> enumTypes = new HashSet<EnumType>();
        enumTypes.add(EnumType.B);
        enumTypes.add(EnumType.A);

        SimpleExpression<String> caseExpression = new CaseBuilder().
                when(qFoo.enumType.in(enumTypes)).
                then(Expressions.constant("x")).
                otherwise(Expressions.constant("y"));

        jpaQuery.from(qFoo).list(caseExpression);
    }
}
