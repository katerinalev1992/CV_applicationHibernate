package com.es.gk.software.rest;

import com.es.gk.software.model.WorkExperience;
import com.es.gk.software.registrator.ExperienceRegistrator;
import com.es.gk.software.repository.WorkExperienceRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by klevytska on 6/23/2016.
 */

@ApplicationScoped
public class WorkExperienceREST {

    @Inject
    private EntityManager entityManager;

    @Inject
    private WorkExperienceRepository workExperienceRepository;

    @Inject
    private ExperienceRegistrator experienceRegistrator;

    @Inject
    private Logger logger;

    @Inject
    private Validator validator;

    @GET
    @Path("/person_id/{person_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WorkExperience> getSkillByPersonId(@PathParam("getByPersonId") long person_id){
        return workExperienceRepository.getByPersonId(person_id);
    }

    @GET
    @Path("/position/{position}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WorkExperience> getWEByPosition(@PathParam("position") String position){
        return workExperienceRepository.getByPosition(position);
    }

    @GET
    @Path("/company/{company}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WorkExperience> getWEByCompanyName(@PathParam("company") String company){
        return workExperienceRepository.getByCompanyName(company);
    }

    @GET
    @Path("/from/{from}/to/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WorkExperience> getWEByPeriod(@PathParam("from") Date from, @PathParam("to") Date to){
        return workExperienceRepository.getByStartDate(from, to);
    }



    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSkill(WorkExperience element) {
        logger.info("PUT save education " + element);
        Response.ResponseBuilder builder = null;
        try {
            experienceRegistrator.update(element);
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("status", "ok");
            builder = Response.status(Response.Status.OK).entity(responseObj);
        } catch (ConstraintViolationException exception) {
            builder = createViolationResponse(exception.getConstraintViolations());
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }
        return builder.build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(WorkExperience element) {
        logger.info("POST save skill " + element);
        Response.ResponseBuilder builder = null;
        try {
            validate(element);
            experienceRegistrator.create(element);
            builder = Response.ok();
        } catch (ConstraintViolationException exception) {
            builder = createViolationResponse(exception.getConstraintViolations());
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    private void validate(WorkExperience element) throws ConstraintViolationException {
        logger.info("Validating menu: " + element);
        Set<ConstraintViolation<WorkExperience>> violations = validator.validate(element);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }

    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        logger.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }
}
