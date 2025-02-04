package org.example.mehrana.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.mehrana.entity.Personnel;
import org.example.mehrana.entity.dto.PersonnelDto;
import org.example.mehrana.exception.DuplicateNationalCodeException;
import org.example.mehrana.exception.SaveRecordException;
import org.example.mehrana.srvice.PersonnelService;

@Path("/personnel")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonnelRest {

    @Inject
    private PersonnelService personnelService;

    @POST
    public Response createPersonnel(PersonnelDto personnel) throws SaveRecordException, DuplicateNationalCodeException {
        personnelService.create(personnel);
        return Response.status(Response.Status.CREATED).build();
    }
}
