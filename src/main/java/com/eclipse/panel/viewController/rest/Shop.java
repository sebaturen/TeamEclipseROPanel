package com.eclipse.panel.viewController.rest;

import com.eclipse.panel.DiscordBot;
import com.eclipse.panel.viewController.rest.keys.APIKeys;
import com.eclipse.panel.viewController.rest.keys.APIKeysType;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/shop")
public class Shop {

    @POST
    @Path("/sold")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reportMonsterLocation(
            String inputData
    ) {
        // Save info
        JsonObject soldData = JsonParser.parseString(inputData).getAsJsonObject();
        if (!soldData.has("api_key") || APIKeys.getAPIKey(soldData.get("api_key").getAsString()).getType() == APIKeysType.UNAUTHORIZED) {
            return Response.status(403).entity("Key not match").build();
        }

        System.out.println(soldData);

        DiscordBot.shared.reportSold(soldData);

        return Response.ok().build();
    }
}
