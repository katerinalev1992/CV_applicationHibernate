package com.es.gk.software.registrator;

import com.es.gk.software.model.Education;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * Created by klevytska on 6/7/2016.
 */
@Stateless
public class EducationRegistrator {

    @Inject
    private Logger logger;

    @Inject
    private EntityManager entityManager;

    public void create(Education education){
        logger.info("Creating new education");
        entityManager.merge(education);
    }

    public void update(Education education) throws Exception{

        logger.info("Updating " + education);
        Education updatedElement = entityManager.find(Education.class, education.getId());
        updatedElement.setCountry(education.getCountry());
        updatedElement.setPlace(education.getPlace());
        updatedElement.setFaculty(education.getFaculty());
        updatedElement.setSpeciality(education.getSpeciality());
        updatedElement.setStart_date(education.getStart_date());
        updatedElement.setEnd_date(education.getEnd_date());
        updatedElement.setPerson_id(education.getPerson_id());
        entityManager.merge(updatedElement);
    }

    public void delete(Education education) throws Exception{
        logger.info("Deleting existing education. Name: " + education);
        entityManager.remove(entityManager.contains(education)? education : entityManager.merge(education));
    }
}
