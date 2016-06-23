package com.es.gk.software.registrator;

import com.es.gk.software.model.Person;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * Created by klevytska on 6/7/2016.
 */

@Stateless
public class PersonRegistrator {

    @Inject
    private Logger logger;

    @Inject
    private EntityManager entityManager;

    public void create(Person person) throws Exception{
        logger.info("Creating new persin. Name: " + person);
        entityManager.merge(person);
    }

    public void update(Person person) throws Exception{

        logger.info("Updating " + person);
        Person updatedElement = entityManager.find(Person.class, person.getId());
        updatedElement.setFirst_name(person.getFirst_name());
        updatedElement.setLast_name(person.getLast_name());
        updatedElement.setBirth_date(person.getBirth_date());
        updatedElement.setCountry(person.getCountry());
        entityManager.merge(updatedElement);
    }

    public void delete(Person person) throws Exception{
        logger.info("Deleting existing person. Name: " + person);
        entityManager.remove(entityManager.contains(person)? person : entityManager.merge(person));
    }
}
