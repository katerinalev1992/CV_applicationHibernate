package com.es.gk.software.rest;

import com.es.gk.software.model.Person;
import com.es.gk.software.registrator.PersonRegistrator;
import com.es.gk.software.repository.PersonRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by klevytska on 6/7/2016.
 */
@Path("/person")
@RequestScoped
public class PersonREST {
    @Inject
    private Logger logger;
    @Inject
    private PersonRepository personRepository;
    @Inject
    private PersonRegistrator personRegistrator;
    @Inject
    private Validator validator;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPersonById(@PathParam("id") long id) {
        return personRepository.getById(id);
    }

    @GET
    @Path("/fisrtName/{firstName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPersonByName(@PathParam("firstName") String firstName){
        return personRepository.getByName(firstName);
    }

    @GET
    @Path("/fisrtName/{firstName}/lastName/{lastName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPersonByName(@PathParam("firstName") String firstName, @PathParam("lastName") String lastName){

        return personRepository.getByFirstNameAndLastName(firstName, lastName);
    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrder(Person element) {
        logger.info("PUT save order " + element);
        Response.ResponseBuilder builder = null;
        try {
            personRegistrator.update(element);
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
    public Response create(Person element) {
        logger.info("POST save menu " + element);
        Response.ResponseBuilder builder = null;
        try {
            validate(element);
            personRegistrator.create(element);
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

    private void validate(Person element) throws ConstraintViolationException {
        logger.info("Validating menu: " + element);
        Set<ConstraintViolation<Person>> violations = validator.validate(element);
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
