package com.eclipse.panel.viewController.rest;

import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/characters")
public class Characters {

    @PUT
    @Path("/{account_id}/{character_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCharacterDetail(
            @PathParam("account_id") int accId,
            @PathParam("character_id") int charId,
            String pjData
    ) {
        JsonObject okInfo = new JsonObject();
        okInfo.addProperty("acc_id", accId);
        okInfo.addProperty("char_id", charId);
        //okInfo.addProperty("pj_data", pjData);

        return Response.ok().entity("acc "+ accId).build();
    }
}
