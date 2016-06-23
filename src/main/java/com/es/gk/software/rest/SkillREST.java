package com.es.gk.software.rest;

import com.es.gk.software.model.Skill;
import com.es.gk.software.registrator.SkillRegistrator;
import com.es.gk.software.repository.SkillRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by klevytska on 6/23/2016.
 */

@ApplicationScoped
public class SkillREST {

    @Inject
    private EntityManager entityManager;

    @Inject
    private SkillRegistrator skillRegistrator;

    @Inject
    private SkillRepository skillRepository;

    @Inject
    private Logger logger;

    @Inject
    private Validator validator;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Skill getSkillById(@PathParam("id") long id) {
        return skillRepository.getById(id);
    }

    @GET
    @Path("/name/{skill_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Skill> getSkillByName(@PathParam("skill_name") String skill_name){
        return skillRepository.getBySkill(skill_name);
    }

    @GET
    @Path("/skill/{skill}/level/{level}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Skill> getSkillByLevel(@PathParam("skill") String skill, @PathParam("level") String level){
        return skillRepository.getBySkillAndLevel(skill, level);
    }

    @GET
    @Path("/person_id/{person_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Skill> getSkillByPersonId(@PathParam("getByPersonId") long person_id){
        return skillRepository.getByPersonId(person_id);
    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSkill(Skill element) {
        logger.info("PUT save education " + element);
        Response.ResponseBuilder builder = null;
        try {
            skillRegistrator.update(element);
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
    public Response create(Skill element) {
        logger.info("POST save skill " + element);
        Response.ResponseBuilder builder = null;
        try {
            validate(element);
            skillRegistrator.create(element);
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

    private void validate(Skill element) throws ConstraintViolationException {
        logger.info("Validating menu: " + element);
        Set<ConstraintViolation<Skill>> violations = validator.validate(element);
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
