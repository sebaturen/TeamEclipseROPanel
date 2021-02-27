package com.eclipse.panel.viewController.rest;

import com.eclipse.panel.DiscordBot;
import com.eclipse.panel.MVPSheetMonitoring;
import com.eclipse.panel.viewController.rest.keys.APIKeys;
import com.eclipse.panel.viewController.rest.keys.APIKeysType;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/monsters")
public class Monster {

    @PUT
    @Path("/location/{monster_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reportMonsterLocation(
            @PathParam("monster_id") int monsterId,
            String inputData
    ) {
        JsonObject okInfo = new JsonObject();
        okInfo.addProperty("monster_id", monsterId);
        //okInfo.addProperty("pj_data", pjData);

        // Save info
        JsonObject monsterData = JsonParser.parseString(inputData).getAsJsonObject();
        if (!monsterData.has("api_key") || APIKeys.getAPIKey(monsterData.get("api_key").getAsString()).getType() == APIKeysType.UNAUTHORIZED) {
            return Response.status(403).entity("Key not match").build();
        }

        DiscordBot.shared.reportMonsterLocation(monsterData);

        return Response.ok().build();

    }

    @POST
    @Path("/died/{map_monster_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reportMonsterDied(
            @PathParam("map_monster_id") int monsterId,
            String inputData
    ) {
        JsonObject okInfo = new JsonObject();
        okInfo.addProperty("monster_id", monsterId);

        // Save info
        JsonObject monsterData = JsonParser.parseString(inputData).getAsJsonObject();
        if (!monsterData.has("api_key") || APIKeys.getAPIKey(monsterData.get("api_key").getAsString()).getType() == APIKeysType.UNAUTHORIZED) {
            return Response.status(403).entity("Key not match").build();
        }

        (new MVPSheetMonitoring()).reportMonsterDied(monsterData);

        return Response.ok().build();
    }
}
