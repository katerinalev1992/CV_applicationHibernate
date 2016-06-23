package com.es.gk.software.repository;

import com.es.gk.software.model.WorkExperience;

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
public class WorkExperienceRepository {

    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;

    public List<WorkExperience> getByPosition(String position){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WorkExperience> criteriaQuery = criteriaBuilder.createQuery(WorkExperience.class);
        Root<WorkExperience> element = criteriaQuery.from(WorkExperience.class);
        criteriaQuery.select(element).where(criteriaBuilder.equal(element.get("position"), position));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<WorkExperience> getByPersonId(Long person_id){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WorkExperience> criteriaQuery = criteriaBuilder.createQuery(WorkExperience.class);
        Root<WorkExperience> element = criteriaQuery.from(WorkExperience.class);
        criteriaQuery.select(element).where(criteriaBuilder.equal(element.get("person_id"), person_id));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<WorkExperience> getByCompanyName (String companyName){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WorkExperience> criteriaQuery = criteriaBuilder.createQuery(WorkExperience.class);
        Root<WorkExperience> element = criteriaQuery.from(WorkExperience.class);
        criteriaQuery.select(element).where(criteriaBuilder.equal(element.get("company_name"), companyName));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<WorkExperience> getByStartDate(Date from, Date to){
        logger.info("Get education by interval: ");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<WorkExperience> criteria = criteriaBuilder.createQuery(WorkExperience.class);
        Root<WorkExperience> element = criteria.from(WorkExperience.class);
        criteria.select(element).where(criteriaBuilder.between(element.<Date>get("date_start"), from, to));
        return entityManager.createQuery(criteria).getResultList();
    }
}
