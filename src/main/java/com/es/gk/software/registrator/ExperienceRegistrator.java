package com.es.gk.software.registrator;

import com.es.gk.software.model.WorkExperience;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * Created by klevytska on 6/7/2016.
 */
@Stateless
public class ExperienceRegistrator {
    @Inject
    private Logger logger;

    @Inject
    private EntityManager entityManager;

    public void create(WorkExperience workExperience){
        logger.info("Creating new education");
        entityManager.merge(workExperience);
    }

    public void update(WorkExperience workExperience) throws Exception{

        logger.info("Updating " + workExperience);
        WorkExperience updatedElement = entityManager.find(WorkExperience.class, workExperience.getId());
        updatedElement.setCompany_name(workExperience.getCompany_name());
        updatedElement.setPosition(workExperience.getPosition());
        updatedElement.setDate_start(workExperience.getDate_start());
        updatedElement.setDate_end(workExperience.getDate_end());
        updatedElement.setPerson_id(workExperience.getPerson_id());

        entityManager.merge(updatedElement);
    }

    public void delete(WorkExperience workExperience) throws Exception{
        logger.info("Deleting existing education. Name: " + workExperience);
        entityManager.remove(entityManager.contains(workExperience)? workExperience : entityManager.merge(workExperience));
    }
}
