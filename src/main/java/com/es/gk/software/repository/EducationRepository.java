package com.es.gk.software.repository;

import com.es.gk.software.model.Education;
import com.es.gk.software.model.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by klevytska on 6/23/2016.
 */

@ApplicationScoped
public class EducationRepository {

    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;

    public Education getById(long id){
        logger.info("Get team by id: " + id);
        return entityManager.find(Education.class, id);
    }

    public List<Education> getByCountry(String country){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Education> criteriaQuery = criteriaBuilder.createQuery(Education.class);
        Root<Education> element = criteriaQuery.from(Education.class);
        criteriaQuery.select(element).where(criteriaBuilder.equal(element.get("country"), country));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Education> getByPlace(String place){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Education> criteriaQuery = criteriaBuilder.createQuery(Education.class);
        Root<Education> element = criteriaQuery.from(Education.class);
        criteriaQuery.select(element).where(criteriaBuilder.equal(element.get("place"), place));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Education> getBySpeciality(String speciality){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Education> criteriaQuery = criteriaBuilder.createQuery(Education.class);
        Root<Education> element = criteriaQuery.from(Education.class);
        criteriaQuery.select(element).where(criteriaBuilder.equal(element.get("speciality"), speciality));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Education> getByPeriod(Date dateStart, Date dateEnd){
        logger.info("Get education by interval: ");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Education> criteria = criteriaBuilder.createQuery(Education.class);
        Root<Education> element = criteria.from(Education.class);
        criteria.select(element).where(criteriaBuilder.between(element.<Date>get("date_start"), dateStart, dateEnd));
        return entityManager.createQuery(criteria).getResultList();
    }

    public List<Education> getByPersonId(Person person_id){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Education> criteriaQuery = criteriaBuilder.createQuery(Education.class);
        Root<Education> element = criteriaQuery.from(Education.class);
        criteriaQuery.select(element).where(criteriaBuilder.equal(element.get("person_id"), person_id));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
