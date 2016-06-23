package com.es.gk.software.rest;

import com.es.gk.software.model.Education;
import com.es.gk.software.registrator.EducationRegistrator;
import com.es.gk.software.repository.EducationRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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

@Path("/education")
@RequestScoped
public class EducationREST {
    @Inject
    private Logger logger;

    @Inject
    private EducationRegistrator educationRegistrator;

    @Inject
    private EducationRepository educationRepository;

    @Inject
    private Validator validator;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Education getEducationById(@PathParam("id") long id) {
        return educationRepository.getById(id);
    }

    @GET
    @Path("/country/{country}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Education> getEducationByName(@PathParam("country") String country){
        return educationRepository.getByCountry(country);
    }

    @GET
    @Path("/speciality/{speciality}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Education> getEducationBySpeciality(@PathParam("speciality") String speciality){
        return educationRepository.getBySpeciality(speciality);
    }

    @GET
    @Path("/place/{place}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Education> getEducationByPlace(@PathParam("place") String place){
        return educationRepository.getByPlace(place);
    }

    @GET
    @Path("/dateStart/{dateStart}/dateEnd/{dateEnd}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Education> getEducationByStartDate(@PathParam("dateStart") Date dateStart, @PathParam("dateEnd") Date dateEnd){
        return educationRepository.getByPeriod(dateStart, dateEnd);
    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEducation(Education element) {
        logger.info("PUT save education " + element);
        Response.ResponseBuilder builder = null;
        try {
            educationRegistrator.update(element);
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
    public Response create(Education element) {
        logger.info("POST save menu " + element);
        Response.ResponseBuilder builder = null;
        try {
            validate(element);
            educationRegistrator.create(element);
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

    private void validate(Education element) throws ConstraintViolationException {
        logger.info("Validating menu: " + element);
        Set<ConstraintViolation<Education>> violations = validator.validate(element);
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
