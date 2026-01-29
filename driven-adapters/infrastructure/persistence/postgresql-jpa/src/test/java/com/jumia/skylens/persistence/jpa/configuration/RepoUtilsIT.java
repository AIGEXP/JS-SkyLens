package com.jumia.skylens.persistence.jpa.configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepoUtilsIT {

    @Autowired
    protected EntityManagerFactory entityManagerFactory;

    public void cleanUpTables() {

        final EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();

        entityManagerFactory
                .unwrap(SessionFactoryImplementor.class)
                .getSchemaManager()
                .truncateMappedObjects();

        em.getTransaction().commit();

        em.close();
    }
}
