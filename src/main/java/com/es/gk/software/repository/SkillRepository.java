package com.es.gk.software.repository;

import com.es.gk.software.model.Skill;

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
 * Created by klevytska on 6/23/2016.
 */

@ApplicationScoped
public class SkillRepository {

    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;

    public Skill getById(long id){
        logger.info("Get skill by id: " + id);
        return entityManager.find(Skill.class, id);
    }

    public List<Skill> getBySkill(String skill){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Skill> criteriaQuery = criteriaBuilder.createQuery(Skill.class);
        Root<Skill> element = criteriaQuery.from(Skill.class);
        criteriaQuery.select(element).where(criteriaBuilder.equal(element.get("skill_name"), skill));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Skill> getBySkillAndLevel(String skill, String level){
        logger.info("Get skill and level");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Skill> criteria = criteriaBuilder.createQuery(Skill.class);
        Predicate lastAndFirstName = criteriaBuilder.conjunction();
        Root<Skill> element = criteria.from(Skill.class);
        lastAndFirstName= criteriaBuilder.and(lastAndFirstName, criteriaBuilder.equal(element.get("skill_name"), skill));
        lastAndFirstName = criteriaBuilder.and(lastAndFirstName, criteriaBuilder.equal(element.get("skill_level"), level));
        criteria.select(element).where(lastAndFirstName).distinct(true);
        return entityManager.createQuery(criteria).getResultList();
    }

    public List<Skill> getByPersonId(Long person_id){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Skill> criteriaQuery = criteriaBuilder.createQuery(Skill.class);
        Root<Skill> element = criteriaQuery.from(Skill.class);
        criteriaQuery.select(element).where(criteriaBuilder.equal(element.get("person_id"), person_id));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
