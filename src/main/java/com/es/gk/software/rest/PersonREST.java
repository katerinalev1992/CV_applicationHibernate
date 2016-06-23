package com.es.gk.software.rest;

import com.es.gk.software.model.Person;
import com.es.gk.software.registrator.PersonRegistrator;
import com.es.gk.software.repository.PersonRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
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

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTeam(Person element) {
        logger.info("POST save team " + element);
        Response.ResponseBuilder builder = null;
        try {
            validate(element);
            Map<String, String> responseObj = new HashMap<>();

            personRegistrator.create(element);
            responseObj.put("Your id: " + personRepository.getByName(element.getFirst_name()).get(0).getId());
            responseObj.put("Link for your first task", "c4ca4238a0b923820dcc509a6f75849b");
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

}
