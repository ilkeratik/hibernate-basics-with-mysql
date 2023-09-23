package com.iky;

import com.iky.entity.Person;
import com.iky.persistence.CustomPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try (EntityManager em = createEMFromPersistenceXML()) {
            em.getTransaction().begin();
            createPersons().forEach(em::persist);
//            deletePerson(em);
//            getPersons(em).forEach(System.out::println);
            em.getTransaction().commit();
        }
    }

    public static EntityManager createProgrammaticEM() {
        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
        EntityManagerFactory fac =
                new HibernatePersistenceProvider().createContainerEntityManagerFactory(
                        new CustomPersistenceUnitInfo(),props);
        return fac.createEntityManager();
    }

    public static EntityManager createEMFromPersistenceXML(){
        EntityManagerFactory fac = Persistence.createEntityManagerFactory("XMLPersistence");
        return fac.createEntityManager();
    }

    public static void deletePerson(EntityManager em){
        Person toDelete = em.find(Person.class, 13);
        em.remove(Objects.requireNonNull(toDelete));
    }

    public static List getPersons(EntityManager em){
        return em.createQuery("select p from Person p").getResultList();
    }

    public static List<Person> createPersons(){
        Person p = new Person();
        p.setName("gamze");
        p.setAge(23);
        p.setLastName("atik");

        Person p2 = new Person();
        p2.setName("irma");
        p2.setAge(23);
        p2.setLastName("atik");

        Person p3 = new Person();
        p3.setName("sena");
        p3.setAge(23);
        p3.setLastName("atik");

        Person p4 = new Person();
        p4.setName("irem");
        p4.setAge(23);
        p4.setLastName("atik");
        return List.of(p,p2,p3,p4);
    }
}
