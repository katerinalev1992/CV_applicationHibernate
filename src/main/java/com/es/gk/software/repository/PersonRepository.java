package com.es.gk.software.repository;

import com.es.gk.software.model.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by klevytska on 6/7/2016.
 */
@ApplicationScoped
public class PersonRepository {

    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;

    public List<Person> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = criteriaBuilder.createQuery(Person.class);
        Root<Person> element = criteria.from(Person.class);
        return entityManager.createQuery(criteria).getResultList();
    }

    public Person getById(long id) {
        logger.info("Get team by id: " + id);
        return entityManager.find(Person.class, id);
    }

    public List<Person> getByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> element = criteriaQuery.from(Person.class);
        criteriaQuery.select(element).where(criteriaBuilder.equal(element.get("first_name"), name));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Person> getByFirstNameAndLastName(String firstName, String lastName){
        logger.info("Get person by last name and first name");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = criteriaBuilder.createQuery(Person.class);
        Predicate lastAndFirstName = criteriaBuilder.conjunction();
        Root<Person> element = criteria.from(Person.class);
        lastAndFirstName= criteriaBuilder.and(lastAndFirstName, criteriaBuilder.equal(element.get("first_name"), firstName));
        lastAndFirstName = criteriaBuilder.and(lastAndFirstName, criteriaBuilder.equal(element.get("last_name"), lastName));
        criteria.select(element).where(lastAndFirstName).distinct(true);
        return entityManager.createQuery(criteria).getResultList();
    }

}
