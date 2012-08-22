package com.example.entity;

import com.mysema.query.jpa.impl.JPAQuery;

import org.junit.Assert;

import org.junit.After;

import org.junit.Before;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import org.junit.Test;


public class EntityTest 
{
    
    EntityManager em;
    
    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerFactory");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
    
    private Dbrd addDbrdToDbre(Dbre dbre, String dbrdName, Long dbrdValue) {
        Dbrd dbrd = new Dbrd();
        dbrd.setName(dbrdName);
        dbrd.setValue(dbrdValue);
        dbrd.setDbre(dbre);
        dbre.getDbrds().add(dbrd);
        return dbrd;
    }
    
    
    @Test
    public void simpleTest() {
        
        
        Dbre dbre1 = new Dbre();
        dbre1.setName("DBRE_1");
        dbre1.setDescription("DBRE_1_DESCRIPTION");
        
        addDbrdToDbre(dbre1, "DBRD_1_1", 1L);
        addDbrdToDbre(dbre1, "DBRD_1_2", 2L);
        addDbrdToDbre(dbre1, "DBRD_1_3", 3L);
        addDbrdToDbre(dbre1, "DBRD_1_1", 4L);
        
        dbre1 = em.merge(dbre1);
        
        Dbre dbre2 = new Dbre();
        dbre2.setName("DBRE_2");
        dbre1.setDescription("DBRE_2_DESCRIPTION");
        
        addDbrdToDbre(dbre2, "DBRD_2_1", 1L);
        addDbrdToDbre(dbre2, "DBRD_2_2", 2L);
        addDbrdToDbre(dbre2, "DBRD_2_3", 3L);
        
        dbre2 = em.merge(dbre2);

        
        QDbrd qDbrd = QDbrd.dbrd;
        
        List<Object[]> result = new JPAQuery(em)
            .from(
                qDbrd)
            .where(
                qDbrd.dbre.eq(dbre1))
            .groupBy(
                qDbrd.dbre,
                qDbrd.name)
            .list(
                qDbrd.dbre,
                qDbrd.name,
                qDbrd.value.sum());
        
        int i = 0;
        for (Object[] row: result) {
            Dbre dbre = (Dbre) row[0];
            String dbrdName = (String) row[1]; 
            Long dbrdSum = (Long) row[2];
            System.out.println(String.format("%s %s %d", dbre, dbrdName, dbrdSum));
            i++;
        }
        
        

    }
    
    
    @After
    public void cleanUp() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }
    
}
