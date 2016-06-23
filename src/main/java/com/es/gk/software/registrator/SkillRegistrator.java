package com.es.gk.software.registrator;

import com.es.gk.software.model.Skill;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * Created by klevytska on 6/7/2016.
 */
@Stateless
public class SkillRegistrator {

    @Inject
    private Logger logger;

    @Inject
    private EntityManager entityManager;

    public void create(Skill skill){
        logger.info("Creating new education");
        entityManager.merge(skill);
    }

    public void update(Skill skill) throws Exception{

        logger.info("Updating " + skill);
        Skill updatedElement = entityManager.find(Skill.class, skill.getId());
        updatedElement.setSkill_name(skill.getSkill_name());
        updatedElement.setSkill_level(skill.getSkill_level());
        updatedElement.setPerson_id(skill.getPerson_id());

        entityManager.merge(updatedElement);
    }

    public void delete(Skill skill) throws Exception{
        logger.info("Deleting existing education. Name: " + skill);
        entityManager.remove(entityManager.contains(skill)? skill : entityManager.merge(skill));
    }
}

