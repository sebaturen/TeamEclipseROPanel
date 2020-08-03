package com.eclipse.panel.viewController.rest;

import com.eclipse.panel.DiscordBot;
import com.eclipse.panel.viewController.ViewController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/monsters")
public class Monster {

    private Gson gson = new GsonBuilder().serializeNulls().create();

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
        if (!monsterData.has("api_key") || APIKeys.getValue(monsterData.get("api_key").getAsString()) == APIKeys.UNKNOWN) {
            return Response.status(403).entity("Key not match").build();
        }

        com.eclipse.panel.gameObject.Monster monster = gson.fromJson(monsterData, com.eclipse.panel.gameObject.Monster.class);
        DiscordBot.shared.reportMonsterLocation(monster);

        return Response.ok().build();

    }
}
