package com.eclipse.panel.viewController.rest;

import com.eclipse.panel.DiscordBot;
import com.eclipse.panel.MVPSheetMonitoring;
import com.eclipse.panel.viewController.ViewController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.*;
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

        APIKeys userShow = APIKeys.getValue(monsterData.get("api_key").getAsString());
        com.eclipse.panel.gameObject.Monster monster = gson.fromJson(monsterData, com.eclipse.panel.gameObject.Monster.class);
        DiscordBot.shared.reportMonsterLocation(monster, userShow);

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
        if (!monsterData.has("api_key") || APIKeys.getValue(monsterData.get("api_key").getAsString()) == APIKeys.UNKNOWN) {
            return Response.status(403).entity("Key not match").build();
        }

        (new MVPSheetMonitoring()).reportMonsterDied(monsterData);

        return Response.ok().build();
    }
}